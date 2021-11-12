import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.*

class CurrentDayFragment:Fragment(R.layout.fragment_current_day) {

    private var nameList = mutableListOf<String>()
    private var weightList = mutableListOf<String>()
    private var caloriesList = mutableListOf<Int>()
    private var eatenList = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(nameList.isEmpty()){
            postToList()
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val breakfast: RecyclerView = requireView().findViewById<View>(R.id.breakfastRecyclerView) as RecyclerView
        val secondBreakfast: RecyclerView = requireView().findViewById<View>(R.id.secondBreakfastRecyclerView) as RecyclerView
        val lunch: RecyclerView = requireView().findViewById<View>(R.id.lunchRecyclerView) as RecyclerView
        val snack: RecyclerView = requireView().findViewById<View>(R.id.snackRecyclerView) as RecyclerView
        val dinner: RecyclerView = requireView().findViewById<View>(R.id.dinnerRecyclerView) as RecyclerView

        breakfast.layoutManager = LinearLayoutManager(activity?.applicationContext)
        breakfast.adapter = ProductAdapter(nameList, weightList, caloriesList, eatenList)



    }


    private fun addToList(name: String, weight: String, calories: Int, eaten: Boolean){
        nameList.add(name)
        weightList.add(weight)
        caloriesList.add(calories)
        eatenList.add(eaten)
    }

    private fun postToList(){
        for(i in 1..5){
            addToList("Produkt $i", "Gramatura $i",354, false)
        }

    }

}