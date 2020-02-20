package ro.david.mobosworkshop.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import ro.david.mobosworkshop.model.InventoryItem

class InventoryDataManager {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_USER_ITEMS = "items"
    }

    private val database = FirebaseFirestore.getInstance()

    fun subscribeForUpdates(userId: String, listener: (list: List<InventoryItem>) -> Unit) {
        database.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_USER_ITEMS)
                .addSnapshotListener { snapshot: QuerySnapshot?, _: FirebaseFirestoreException? ->
                    snapshot?.apply {
                        listener(toObjects(InventoryItem::class.java))
                    }
                }
    }

    fun add(item: InventoryItem, userId: String, onComplete: () -> Unit) {
        database.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_USER_ITEMS)
                .add(item)
                .addOnCompleteListener { onComplete() }
    }

}