package ro.david.mobosworkshop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.david.mobosworkshop.model.InventoryItem

class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<List<InventoryItem>>().apply {
        value = listOf(InventoryItem("Cheese", 1), InventoryItem("Cottage cheese", 3))
    }
    val list: LiveData<List<InventoryItem>> = _list

}