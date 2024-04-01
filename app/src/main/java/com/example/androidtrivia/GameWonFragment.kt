package com.example.androidtrivia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.navigation.findNavController
import com.example.androidtrivia.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_game_won,container,false)
      binding.WinButton.setOnClickListener { view: View ->
          view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
      }
        var args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context,"Congratulations you got ${args.numCorrect}/${args.numQuestions} correct", Toast.LENGTH_SHORT).show()
        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu , menu)
        if(null == getShareIntent().resolveActivity(requireActivity().packageManager))
            menu?.findItem(R.id.share)?.setVisible(false)

    }
    private fun getShareIntent(): Intent {
        var args = GameWonFragmentArgs.fromBundle(requireArguments())
      return ShareCompat.IntentBuilder.from(requireActivity()).
      setText(getString(R.string.share_success_text,args.numCorrect ,args.numQuestions))
          .setType("text/plain")
          .intent
    }
    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = "Android Trivia"
    }

}
