package com.example.feature_transaction.ui.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_transaction.domain.response.TransactionDO
import com.example.feature_transaction.domain.useCases.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    val getTransactionUseCase: GetTransactionUseCase,
    val handleErrorUseCase: HandleErrorUseCase
) : BaseViewModel<TransactionContract.State, TransactionContract.Effect>(TransactionContract.State(),handleErrorUseCase ) {

    init {
        viewModelScope.launch {
         val initialData = loadTransactions(offset = 0,
                userPullRequest = false,
                isInitialData = true)
            updateState { it.copy(transactionWithDay = initialData ) }

        }
    }

    private suspend fun loadTransactions(
        offset : Int,
        userPullRequest : Boolean,
        isInitialData : Boolean
    ) : Map<String, List<TransactionDO>>{
          if (isInitialData){
              updateState { it.copy(isLoading = true) }
          } else{
              updateState { it.copy(isAppendLoading = true) }

          }
              when(val res = getTransactionUseCase(offset, userPullRequest)){
                is ResultWrapper.Success -> {
                     updateState{ it.copy(isLoading = false, isAppendLoading = false) }
                    val combined = groupByDay(res.data)
                    return combined

                }
                is ResultWrapper.Error -> {
                    updateState { it.copy(isLoading = false, isAppendLoading = false) }
                    handleError(res.error)
                    return emptyMap()
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
        val beforeWaitingTransactionList = currentState.transactionWithDay
        if(pagingMutex.isLocked) return

        viewModelScope.launch {
            pagingMutex.withLock {
                val afterTransactionList = currentState().transactionWithDay
             if(beforeWaitingTransactionList != afterTransactionList || currentState.paginationIsFinished){
               return@withLock
             }
                val newTransactionWithKey = loadTransactions(offset = currentState.transactionWithDay.values.sumOf { it.size }, userPullRequest = false, isInitialData = false)
             //   val transactionList = newTransaction.map { it.value }
               /* if(transactionList.lastOrNull()?. == currentState.transactionList.lastOrNull()?.id){
                    return@withLock
                }*/

                updateState { it ->
                    val merged = (it.transactionWithDay.keys + newTransactionWithKey.keys).associateWith { day ->
                        (it.transactionWithDay[day] ?: emptyList()) + (newTransactionWithKey[day] ?: emptyList())
                    }
                    it.copy(transactionWithDay = merged, paginationIsFinished = newTransactionWithKey.values.sumOf { it.size } != TransactionContract.MAX_PAGE) }
            }
        }

    }

    private fun groupByDay( list : List<TransactionDO>) : Map<String, List<TransactionDO>>{
        return list.groupBy { it.timestamp.substring(0,10) }
    }
    private fun reloadTransactions() {
        viewModelScope.launch {
            val refreshedTransactions = loadTransactions(offset = 0, userPullRequest = true, isInitialData = true)
            updateState { it.copy(transactionWithDay = refreshedTransactions, paginationIsFinished = false) }
        }
    }



    override fun showMessage(message: Int) {
       viewModelScope.launch {
           sendEffect(TransactionContract.Effect.ShowErrorMessage(message))
       }
    }

}