package ro.david.mobosworkshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.david.mobosworkshop.R

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var inventoryAdapter: InventoryAdapter

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

        homeViewModel.list.observe(viewLifecycleOwner, Observer { items ->
            inventoryAdapter.update(items)
        })
    }
}