package ro.david.mobosworkshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ro.david.mobosworkshop.R
import ro.david.mobosworkshop.data.AuthenticationManager
import ro.david.mobosworkshop.data.InventoryDataManager
import ro.david.mobosworkshop.model.InventoryItem

class CreateItemFragment : Fragment() {

    private val dataManager = InventoryDataManager()
    private val authManager = AuthenticationManager()

    private lateinit var titleEdit: TextInputEditText
    private lateinit var quantityEdit: TextInputEditText
    private lateinit var saveButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            layoutInflater.inflate(R.layout.fragment_add_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEdit = view.findViewById(R.id.title)
        quantityEdit = view.findViewById(R.id.quantity)
        saveButton = view.findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            val item = InventoryItem(titleEdit.text.toString(), quantityEdit.text.toString().toInt())
            authManager.getUser()?.let { user ->
                dataManager.add(item, user.uid) {
                    activity?.onBackPressed()
                }
            } ?: run {
                activity?.onBackPressed()
            }
        }
    }

    companion object {
        const val TAG = "CreateItemFragment"
    }
}