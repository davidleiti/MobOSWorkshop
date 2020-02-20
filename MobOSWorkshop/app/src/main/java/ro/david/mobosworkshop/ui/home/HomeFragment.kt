package ro.david.mobosworkshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ro.david.mobosworkshop.R
import ro.david.mobosworkshop.data.AuthenticationManager

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var inventoryAdapter: InventoryAdapter
    private val authManager = AuthenticationManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inventoryAdapter = InventoryAdapter()
        view.findViewById<RecyclerView>(R.id.list_inventory).apply {
            adapter = inventoryAdapter
            layoutManager = LinearLayoutManager(context)
        }

        view.findViewById<FloatingActionButton>(R.id.create_item).apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_create_item)
            }
        }

        homeViewModel.list.observe(viewLifecycleOwner, Observer { items ->
            inventoryAdapter.update(items)
        })

        authManager.getUser()?.let { user ->
            homeViewModel.requestItems(user.uid)
        } ?: run {
            Snackbar.make(view, "You need to be signed in to use the inventory!", Snackbar.LENGTH_INDEFINITE).show()
        }
    }
}