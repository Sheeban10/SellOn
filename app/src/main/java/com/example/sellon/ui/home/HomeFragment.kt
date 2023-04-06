package com.example.sellon.ui.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sellon.BaseActivity
import com.example.sellon.MainActivity
import com.example.sellon.R
import com.example.sellon.databinding.FragmentHomeBinding
import com.example.sellon.model.CategoriesModel
import com.example.sellon.ui.home.adapter.CategoriesAdapter
import com.example.sellon.utilities.Constants
import com.example.sellon.utilities.SharedPref
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoriesModel: MutableList<CategoriesModel>
    private lateinit var categoriesAdapter: CategoriesAdapter
    val db = FirebaseFirestore.getInstance().collection("Categories")





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = layoutInflater.inflate(R.layout.fragment_home, container , false)

        return root

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCategoryList()

        tvCityName.text = SharedPref(requireActivity()).getString(Constants.CITY_NAME)



    }

    @SuppressLint("SuspiciousIndentation")
    private fun getCategoryList() {
        db.get().addOnSuccessListener {
        categoriesModel = it.toObjects(CategoriesModel::class.java)
            setAdapter()

        }
    }

    private fun setAdapter() {

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3)
            categoriesAdapter = CategoriesAdapter(categoriesModel)
            rv_categories.adapter = categoriesAdapter

        }
    }

}



//    class HomeFragment : Fragment() {
//        private lateinit var categoriesAdapter: CategoriesAdapter
//        private lateinit var categoriesModel: MutableList<CategoriesModel>
//        val db = FirebaseFirestore.getInstance()
//
//        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//            val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//            // Set up RecyclerView and adapter
//            val recyclerView = view.findViewById<RecyclerView>(R.id.rv_categories)
//            recyclerView.layoutManager = GridLayoutManager(context, 3)
//            categoriesModel = mutableListOf()
//            categoriesAdapter = CategoriesAdapter(categoriesModel)
//            recyclerView.adapter = categoriesAdapter
//
//            return view
//        }
//
//        override fun onActivityCreated(savedInstanceState: Bundle?) {
//            super.onActivityCreated(savedInstanceState)
//
//            tvCityName.text = SharedPref(requireActivity()).getString(Constants.CITY_NAME)
//
//            getCategoryList()
//        }
//
//        private fun getCategoryList() {
//            db.collection("Categories").get().addOnSuccessListener {
//                categoriesModel.clear()
//                categoriesModel.addAll(it.toObjects(CategoriesModel::class.java))
//                categoriesAdapter.notifyDataSetChanged()
//            }
//        }
//    }






//class HomeFragment :Fragment(){

// private lateinit var categoriesAdapter: CategoriesAdapter
//    private lateinit var categoriesModel: MutableList<CategoriesModel>
//
//    val db = FirebaseFirestore.getInstance()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val root = layoutInflater.inflate(R.layout.fragment_home, container, false)
//
//
//        return root
//
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        tvCityName.text = SharedPref(requireActivity()).getString(Constants.CITY_NAME)
//
//        getCategoryList()
//    }
//
//    @SuppressLint("SuspiciousIndentation")
//    private fun getCategoryList() {
//        db.collection("Categories").get().addOnSuccessListener {
//        categoriesModel = it.toObjects(CategoriesModel::class.java)
//            setAdapter()
//
//        }
//    }
//
//    private fun setAdapter() {
//        rv_categories.layoutManager = GridLayoutManager(context, 3)
//
//        categoriesAdapter = CategoriesAdapter(categoriesModel)
//        rv_categories.adapter = categoriesAdapter
//    }
//
//}




//    class HomeFragment : Fragment() {
//      val db = FirebaseFirestore.getInstance()
//        private lateinit var categoriesAdapter: CategoriesAdapter
//        private lateinit var categoriesModel: MutableList<CategoriesModel>
//        val db = FirebaseFirestore.getInstance()
//
//        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//            val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//            // Set up RecyclerView and adapter
//            val recyclerView = view.findViewById<RecyclerView>(R.id.rv_categories)
//            recyclerView.layoutManager = GridLayoutManager(context, 3)
//            categoriesModel = mutableListOf()
//            categoriesAdapter = CategoriesAdapter(categoriesModel)
//            recyclerView.adapter = categoriesAdapter
//
//            return view
//        }
//
//        override fun onActivityCreated(savedInstanceState: Bundle?) {
//            super.onActivityCreated(savedInstanceState)
//
//            tvCityName.text = SharedPref(requireActivity()).getString(Constants.CITY_NAME)
//
//            getCategoryList()
//        }
//
//        private fun getCategoryList() {
//            db.collection("Categories").get().addOnSuccessListener {
//                categoriesModel.clear()
//                categoriesModel.addAll(it.toObjects(CategoriesModel::class.java))
//                categoriesAdapter.notifyDataSetChanged()
//            }
//        }
//    }
//
//}
