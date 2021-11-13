import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.*

class CurrentDayFragment:Fragment(R.layout.fragment_current_day) {

    private var breakfastNameList = mutableListOf<String>()
    private var breakfastWeightList = mutableListOf<String>()
    private var breakfastCaloriesList = mutableListOf<Int>()
    private var breakfastEatenList = mutableListOf<Boolean>()

    private var secondBreakfastNameList = mutableListOf<String>()
    private var secondBreakfastWeightList = mutableListOf<String>()
    private var secondBreakfastCaloriesList = mutableListOf<Int>()
    private var secondBreakfastEatenList = mutableListOf<Boolean>()

    private var lunchNameList = mutableListOf<String>()
    private var lunchWeightList = mutableListOf<String>()
    private var lunchCaloriesList = mutableListOf<Int>()
    private var lunchEatenList = mutableListOf<Boolean>()

    private var snackNameList = mutableListOf<String>()
    private var snackWeightList = mutableListOf<String>()
    private var snackCaloriesList = mutableListOf<Int>()
    private var snackEatenList = mutableListOf<Boolean>()

    private var dinnerNameList = mutableListOf<String>()
    private var dinnerWeightList = mutableListOf<String>()
    private var dinnerCaloriesList = mutableListOf<Int>()
    private var dinnerEatenList = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(breakfastNameList.isEmpty()){
            postBreakfastToList()
        }
        if(secondBreakfastNameList.isEmpty()){
            postSecondBreakfastToList()
        }
        if(lunchNameList.isEmpty()){
            postLunchToList()
        }
        if(snackNameList.isEmpty()){
            postSnackToList()
        }
        if(dinnerNameList.isEmpty()){
            postDinnerToList()
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
        breakfast.adapter = ProductAdapter(breakfastNameList, breakfastWeightList, breakfastCaloriesList, breakfastEatenList)
        //breakfast.isNestedScrollingEnabled = false

        secondBreakfast.layoutManager = LinearLayoutManager(activity?.applicationContext)
        secondBreakfast.adapter = ProductAdapter(secondBreakfastNameList, secondBreakfastWeightList, secondBreakfastCaloriesList, secondBreakfastEatenList)
        //secondBreakfast.isNestedScrollingEnabled = false

        lunch.layoutManager = LinearLayoutManager(activity?.applicationContext)
        lunch.adapter = ProductAdapter(lunchNameList, lunchWeightList, lunchCaloriesList, lunchEatenList)
        //lunch.isNestedScrollingEnabled = false

        snack.layoutManager = LinearLayoutManager(activity?.applicationContext)
        snack.adapter = ProductAdapter(snackNameList, snackWeightList, snackCaloriesList, snackEatenList)
        //snack.isNestedScrollingEnabled = false

        dinner.layoutManager = LinearLayoutManager(activity?.applicationContext)
        dinner.adapter = ProductAdapter(dinnerNameList, dinnerWeightList, dinnerCaloriesList, dinnerEatenList)
        //dinner.isNestedScrollingEnabled = false



    }


    private fun addToBreakfastList(name: String, weight: String, calories: Int, eaten: Boolean){
        breakfastNameList.add(name)
        breakfastWeightList.add(weight)
        breakfastCaloriesList.add(calories)
        breakfastEatenList.add(eaten)
    }

    private fun postBreakfastToList(){
        for(i in 1..5){
            addToBreakfastList("Produkt $i", "Gramatura $i",354, false)
        }

    }

    private fun addToSecondBreakfastList(name: String, weight: String, calories: Int, eaten: Boolean){
        secondBreakfastNameList.add(name)
        secondBreakfastWeightList.add(weight)
        secondBreakfastCaloriesList.add(calories)
        secondBreakfastEatenList.add(eaten)
    }

    private fun postSecondBreakfastToList(){
        for(i in 1..5){
            addToSecondBreakfastList("Produkt $i", "Gramatura $i",128, false)
        }

    }

    private fun addToLunchList(name: String, weight: String, calories: Int, eaten: Boolean){
        lunchNameList.add(name)
        lunchWeightList.add(weight)
        lunchCaloriesList.add(calories)
        lunchEatenList.add(eaten)
    }

    private fun postLunchToList(){
        for(i in 1..5){
            addToLunchList("Produkt $i", "Gramatura $i",608, false)
        }

    }

    private fun addToSnackList(name: String, weight: String, calories: Int, eaten: Boolean){
        snackNameList.add(name)
        snackWeightList.add(weight)
        snackCaloriesList.add(calories)
        snackEatenList.add(eaten)
    }

    private fun postSnackToList(){
        for(i in 1..5){
            addToSnackList("Produkt $i", "Gramatura $i",561, false)
        }

    }

    private fun addToDinnerList(name: String, weight: String, calories: Int, eaten: Boolean){
        dinnerNameList.add(name)
        dinnerWeightList.add(weight)
        dinnerCaloriesList.add(calories)
        dinnerEatenList.add(eaten)
    }

    private fun postDinnerToList(){
        for(i in 1..30){
            addToDinnerList("Produkt $i", "Gramatura $i",202, false)
        }

    }

}