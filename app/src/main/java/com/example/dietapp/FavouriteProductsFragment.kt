import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.DietAdapter
import com.example.dietapp.FavouriteProductsAdapter
import com.example.dietapp.R


class FavouriteProductsFragment:Fragment(R.layout.fragment_favourite_products) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<DietAdapter.ViewHolder>? = null

    private var nameList = mutableListOf<String>()
    private var proteinsList = mutableListOf<String>()
    private var fatsList = mutableListOf<String>()
    private var carbsList = mutableListOf<String>()
    private var caloriesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(nameList.isEmpty()){
            postToList()
        }
    }



    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView: RecyclerView = requireView().findViewById<View>(R.id.favouriteProductsRecycler) as RecyclerView



        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = FavouriteProductsAdapter(nameList, proteinsList, fatsList, carbsList, caloriesList)
        }
    }

    private fun addToList(name: String, proteins: String, fats: String, carbs: String, calories: String){
        nameList.add(name)
        proteinsList.add(proteins)
        fatsList.add(fats)
        carbsList.add(carbs)
        caloriesList.add(calories)
    }

    private fun postToList(){
        for(i in 1..10){
            addToList("name $i", "50 g", "79 g", "129  g", "2399 kcal")
        }

    }
}
