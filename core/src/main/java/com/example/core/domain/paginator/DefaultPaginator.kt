/*package com.example.core.domain.paginator

import com.example.core.domain.feature.Paginator
import com.example.core.domain.model.AppError
import com.example.core.domain.model.ResultWrapper
import java.util.concurrent.atomic.AtomicBoolean

class DefaultPaginator<Key,Item>(
    private val initialKey : Key,
    private  val getNextKey : (List<Item>) -> Key,
    private  val onRequest: suspend (Key) -> ResultWrapper<List<Item>>,
    private  val onError : suspend (AppError) -> Unit,
    private  val onSuccess : suspend (Key, List<Item>) -> Unit
) : Paginator {
    var currentPage = initialKey
    var isRequestingNextPage : AtomicBoolean = AtomicBoolean(false)
    override suspend fun loadNextItems() {
        if (isRequestingNextPage.getAndSet(true)) return

        when(val result = onRequest(currentPage)){
            is ResultWrapper.Error -> {
                isRequestingNextPage.set(false)
                onError(result.error)
            }
            is ResultWrapper.Success ->{
                currentPage = getNextKey(result.data)
                isRequestingNextPage.set(false)
                onSuccess(currentPage, result.data)
            }

        }



    }

    override fun reset() {
        currentPage = initialKey
        isRequestingNextPage.set(false)

    }

}*/

/*

     i e sasen deyisen bir list yaratmaq lazimdir
     transactionlari bu i key ine esasen set etmek lazimdir
*   state(transactionList : List<Pair< String, T>> = emptyList )

     val transactionWithKeyList = List<Pair<String, T>>

*     when(res){
*      is successfull ->  {
*        res.data-> List<Transaction>
         val combined = res.data.associatedBy(time->15) // key , value

         combined.forEach { i, transaction ->(i -> 12,13...., transaction -> 234,235)
             transactionWithKey.add(
               i to transaction
             )

}


* )
* */