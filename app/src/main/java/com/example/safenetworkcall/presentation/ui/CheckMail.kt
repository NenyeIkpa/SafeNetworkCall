package com.example.safenetworkcall.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.safenetworkcall.R
import com.example.safenetworkcall.databinding.FragmentCheckMailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckMail : Fragment() {
    private lateinit var binding: FragmentCheckMailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_check_mail, container, false)
        return binding.rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initializing view binding
        binding = FragmentCheckMailBinding.bind(view)

        // Try another email if message not received or check spam filter.
        binding.consumerCheckMailTextView5.setOnClickListener {
           //to do
        }

        // navigate to consumer create new password page
        binding.consumerCheckMailButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                this.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "There is no email client installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}