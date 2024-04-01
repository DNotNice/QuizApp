package com.example.androidtrivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.androidtrivia.databinding.FragmentGameBinding
import java.nio.file.Files.size

class GameFragment : Fragment() {
data class Question (
val text :String ,
val answers : List<String>
)
  private  var question: MutableList<Question> = mutableListOf(
      Question(
      text = "What is Android Jetpack ?" , answers = listOf("all of these" ,"tools","documentation","libraries")),
      Question(text = "Base class for layout",answers = listOf("ViewGroup","ViewSet","ViewCollection","ViewRoot")),
      Question(text = "Layout for complex Screens?",
          answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
      Question(text = "Pushing structured data into a Layout?",
          answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")),
      Question(text = "Inflate layout in fragments?",
          answers = listOf("onCreateView", "onViewCreated", "onCreateLayout", "onInflateLayout")),
      Question(text = "Build system for Android?",
          answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
      Question(text = "Android vector format?",
          answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
      Question(text = "Android Navigation Component?",
          answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
      Question(text = "Registers app with launcher?",
          answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
      Question(text = "Mark a layout for Data Binding?",
          answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
  )
  lateinit var currentquestion : Question
  lateinit var answers: List<String>
  private var questionIndex = 0 ;
private val numQuestions = Math.min((question.size +1)/2 , 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,R.layout.fragment_game,container,false)
        randomizeQuestion()
        binding.game = this
       binding.SubmitButton.setOnClickListener  @Suppress("UNUSED_ANONYMOUS_PARAMETER"){
            view:View ->
           val checkId = binding.questionRadioGroup.checkedRadioButtonId
           if(-1 != checkId){
               var indexAnswer = 0
               when(checkId){
                   R.id.secondAnswerRadioButton -> indexAnswer =1
                   R.id.thirdAnswerRadioButton -> indexAnswer =2
                   R.id.fourthAnswerRadioButton-> indexAnswer = 3
               }
               if(answers[indexAnswer] == currentquestion.answers[0]){
                   questionIndex++
                   if(questionIndex < numQuestions){
                      currentquestion = question[questionIndex]
                       setQuestion()
                       binding.invalidateAll()

                   }
                   else{
                       //you won .. move to the game won fragment
                       view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions , questionIndex))
                   }
               }else {
                   // move to lost fragment
                   view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
               } } }
     return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = "Android Trivia"
    }

    private fun randomizeQuestion() {
        question.shuffle()
        questionIndex = 0 ;
        setQuestion()
    }

    private fun setQuestion() {
      currentquestion = question[questionIndex]
      answers = currentquestion.answers.toMutableList()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)

    }

}