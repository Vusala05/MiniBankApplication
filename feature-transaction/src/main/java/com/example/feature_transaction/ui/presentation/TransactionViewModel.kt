package com.example.feature_transaction.ui.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_transaction.domain.response.TransactionDO
import com.example.feature_transaction.domain.useCases.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    val getTransactionUseCase: GetTransactionUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
) : BaseViewModel<TransactionContract.State, TransactionContract.Effect>(TransactionContract.State(),handleErrorUseCase ) {

    init {
        viewModelScope.launch {
         val initialData = loadTransactions(offset = 0,userPullRequest = false)
            if(initialData!=null){
                updateState { it.copy(transactionList = initialData ,
                    paginationIsFinished = initialData.size != TransactionContract.MAX_PAGE) }

            }
        }
        observeTransactionAndGrouped()

    }

    private suspend fun loadTransactions(
        offset : Int,
        userPullRequest : Boolean,
    ) : List<TransactionDO>? {

        updateState { it.copy(isLoading = true, isPageLoading = true) }
        if(userPullRequest){
            updateState {it.copy(isRefreshing = true) }
        }
        when(val res = getTransactionUseCase(offset, userPullRequest)){
                is ResultWrapper.Success -> {
                     updateState{ it.copy(isLoading = false, isPageLoading = false, isRefreshing = false) }

                    return res.data

                }
                is ResultWrapper.Error -> {
                    updateState { it.copy(isLoading = false, isPageLoading = false, isRefreshing = false) }
                    handleError(res.error)

                    return null
                }
            }


    }

    fun handleIntent(intent: TransactionContract.Intent){
     when(intent){
         is TransactionContract.Intent.LoadNextPage -> {
             loadNextPage()
         }
         is TransactionContract.Intent.ReloadPage -> {
             reloadTransactions()
         }

     }
    }

    val pagingMutex = Mutex()
    private  fun loadNextPage(){
        val currentState = currentState()
        val beforeWaitingTransactionList = currentState.transactionList
        if(pagingMutex.isLocked) return

        viewModelScope.launch {
            pagingMutex.withLock {
                val afterState = currentState()
                val afterTransactionList = afterState.transactionList
                if (beforeWaitingTransactionList != afterTransactionList || afterState.paginationIsFinished) {
                    return@withLock
                }
                val newTransactionList = loadTransactions(
                    offset = afterState.transactionList.size,
                    userPullRequest = false
                )


                if (newTransactionList != null) {
                    updateState {
                        it.copy(
                            transactionList = it.transactionList + newTransactionList,
                            paginationIsFinished = newTransactionList.size != TransactionContract.MAX_PAGE
                        )
                    }
                }
            }
        }

    }

    private fun groupByDay( list : List<TransactionDO>) : Map<String, List<TransactionDO>>{
        return list.groupBy { it.timestamp.substring(0,10) }
    }
    private fun reloadTransactions() {
            viewModelScope.launch {
                pagingMutex.withLock {
                val refreshedTransactions = loadTransactions(offset = 0, userPullRequest = true)
                    if(refreshedTransactions!=null) {
                        updateState {
                            it.copy(
                                transactionList = refreshedTransactions,
                                paginationIsFinished = false
                            )
                        }
                    }
            }
        }
    }

    private fun observeTransactionAndGrouped() {
        viewModelScope.launch(Dispatchers.IO) {
            state.distinctUntilChangedBy { it.transactionList }
                .collectLatest {
                  val grouped = groupByDay(it.transactionList)

                    val groupedTransactionList = grouped.map { (string, dOS) ->
                        GroupedTransactionList(
                            time = string,
                            transactionList = dOS
                        )
                    }

                    updateState { it.copy(groupedTransactionList = groupedTransactionList)}
                }


        }
    }




    override fun showMessage(message: Int) {
       viewModelScope.launch {
           sendEffect(TransactionContract.Effect.ShowErrorMessage(message))
       }
    }




   /* private suspend fun observeFilterAndRefresh(){
        state.distinctUntilChanged { old, new ->
            val dateReEqual = old.transactionWithDay == new.transactionWithDay
            dateReEqual
        } .collectLatest {
            //code block
        }
    }*/

    /*if(newTransactionList?.lastOrNull()?. == afterState.transactionList.lastOrNull()?.id){
               return@withLock
               }*/



}
