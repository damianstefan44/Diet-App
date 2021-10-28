import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.DietAdapter
import com.example.dietapp.R


class MyDietsFragment:Fragment(R.layout.fragment_my_diets) {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<DietAdapter.ViewHolder>? = null

    private var nameList = mutableListOf<String>()
    private var caloriesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(nameList.isEmpty()){
            postToList()
        }
    }



        override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
            super.onViewCreated(itemView, savedInstanceState)
            val recyclerView: RecyclerView = requireView().findViewById<View>(R.id.myDietsRecycler) as RecyclerView


            recyclerView.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity)
                // set the custom adapter to the RecyclerView
                adapter = DietAdapter(nameList, caloriesList)
            }
        }

    private fun addToList(name: String, calories: String){
        nameList.add(name)
        caloriesList.add(calories)
    }

    private fun postToList(){
        for(i in 1..10){
            addToList("name $i", "2399 kcal")
        }

    }
}

