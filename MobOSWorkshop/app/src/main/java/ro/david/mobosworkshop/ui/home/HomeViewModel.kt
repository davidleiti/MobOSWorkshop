package ro.david.mobosworkshop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.david.mobosworkshop.data.InventoryDataManager
import ro.david.mobosworkshop.model.InventoryItem

class HomeViewModel : ViewModel() {

    private val dataManager = InventoryDataManager()

    private val _list = MutableLiveData<List<InventoryItem>>()
    val list: LiveData<List<InventoryItem>> = _list

    fun requestItems(userId: String) {
        dataManager.subscribeForUpdates(userId) { list -> _list.value = list }
    }

}