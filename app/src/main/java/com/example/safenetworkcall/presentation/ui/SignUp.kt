package com.example.safenetworkcall.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.safenetworkcall.*
import com.example.safenetworkcall.data.SignUpUser
import com.example.safenetworkcall.data.remote.model.Location
import com.example.safenetworkcall.data.remote.model.User
import com.example.safenetworkcall.databinding.FragmentSignUpBinding
import com.example.safenetworkcall.presentation.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
private const val TAG = "TAG"
@AndroidEntryPoint
class SignUp : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var userInfo: SignUpUser
    private val companiesById = hashMapOf<String, String>()
    private  val Errors = mutableSetOf<TextInputLayout>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCompanies()
        viewModel.listOfCompanies.observe(
            viewLifecycleOwner
        ) { list ->
            list.data?.data?.map { company ->
                companiesById.put(company.companyName, company.companyId)
            }
            companyDropDown()
        }
        dropDown()

        binding.supplierSignupLayoutTextViewSignIn.setOnClickListener {

        }
        binding.supplierSignupLayoutTextViewBack.setOnClickListener {

        }

        binding.supplierSignupBtn.setOnClickListener {
            clearErrors()//clear errors once you click on sign up,
            // then it there is any other errors it will be shown
            binding.supplierSignupBtn.isEnabled = false
            binding.supplierSignupBtn.text = "Registering"
            binding.supplierRegistrationProgressBar.visibility = View.VISIBLE
            val firstName = binding.etFirstNameSupplierSignUp.text.toString()
            val lastName = binding.etLastNameSupplierSignUp.text.toString()
            val age = if(binding.etAgeSupplierSignUp.text.toString().isBlank())
                0 else binding.etAgeSupplierSignUp.text.toString().toInt()
            //note that you cannot call toInt() method on an empty string
            // e.g "".toInt() will throw exception
            //so instead just check if it is empty or check when value == "" then assign 0 to age
            val gender = binding.sexAutoTextView.text.toString()
            val email = binding.etEmailSupplierSignUp.text.toString()
            val companyName = binding.companyAutoTextView.text
            val companyId = binding.etCompanyId.text.toString()
            val password = binding.etPasswordSupplierSignUp.text.toString()
            val confirmPassword = binding.etConfirmPasswordSupplierSignUp.text.toString()
            val phoneNumber = binding.etPhoneSupplierSignUp.text.toString()
            val location = binding.etAddressSupplierSignUp.text.toString()
          userInfo = SignUpUser(companyId, User(firstName, lastName, age, gender, email, password, confirmPassword, phoneNumber, Location(location)))

//            userInfo = SignUpUser("ab278d49-50ed-403e-9a4a-e8ad2570766f", User("Firstname", "Lastname", age, "male", "nnabuike.ikpa@gmail.com", "Netizen@123","Netizen@123", "08037771010", Location("location")))

            Log.d(TAG, "onViewCreated: $userInfo ")
//
            if (!validateCompanySelection(companyId)){
                setError(binding.supplierCompanyContainer,
                "Select a company")
                showSignUpButton()
            }
            if (!validateFirstNameInput(firstName)){
                setError(binding.supplierFirstNameContainer,
                    "Should start with a capital letter, at least 3 character")
                showSignUpButton()//reduce tautology in code
            }
            if (!validateLastNameInput(lastName)) {
                setError(binding.supplierLastNameContainer,
                    "Atleast 1 letter, atleast 3 character")
                showSignUpButton()
            }
            if (validateAgeInput(age) != 0) {
                if (validateAgeInput(age)==1)
                    setError(binding.supplierAgeContainer,"age field is required")
                    else if (validateAgeInput(age)==2) //gives more detail reason for error
                        setError(binding.supplierAgeContainer,
                            "you are too young to register")//edit this later
                        else if (validateAgeInput(age) == 3)
                            setError(binding.supplierAgeContainer,"enter a valid age")
                showSignUpButton()
            }
            if (!validateEmailInput(email)) {
                setError(binding.supplierEmailContainer,"Invalid email")
                 showSignUpButton()
            }
            if (!validatePhoneInput(phoneNumber)) {
                setError(binding.supplierPhoneContainer,
                    "Starts with '0' followed by '7', '8' or '9' and 11 characters")
                showSignUpButton()
            }
            if (!validateSex(gender)) {
                setError(binding.supplierGenderContainer, "Gender must be male or female")
                showSignUpButton()
            }
            if (!validatePasswordInput(password)) {
                setError(binding.supplierPasswordContainer,
                    "At least 1 uppercase, 1 lowercase, 1 special character 1 digit and at least 8 characters")
                showSignUpButton()
            }
            if (!validateConfirmPassword(password, confirmPassword)) {
                setError(binding.supplierConfirmPasswordContainer, "Password doesn't match")
                showSignUpButton()
            }
            if (validateCompanySelection(companyId)&&
                validateFirstNameInput(firstName) &&
                validateLastNameInput(lastName) &&
                validateAgeInput(age) == 0 &&
                validateEmailInput(email) &&
                validatePasswordInput(password) &&
                validatePhoneInput(phoneNumber) &&
                validateSex(gender)
            ) {
                viewModel.registerSupplier(userInfo)
                viewModel.signUpResponse.observe(viewLifecycleOwner) {
                    if (it.data?.success == true) {
                        Snackbar.make(view, it.data.message.toString(), Snackbar.LENGTH_LONG).setAnchorView(binding.supplierSignupBtn).show()
                        binding.supplierRegistrationProgressBar.visibility = View.GONE
                       findNavController().navigate(R.id.action_signUp_to_checkMail)
                        binding.etFirstNameSupplierSignUp.text?.clear()
                        binding.etLastNameSupplierSignUp.text?.clear()
                        binding.supplierSignupBtn.text = "Register"
                        binding.etPasswordSupplierSignUp.text?.clear()
                        binding.etConfirmPasswordSupplierSignUp.text?.clear()
                        binding.etPhoneSupplierSignUp.text?.clear()
                        binding.etAgeSupplierSignUp.text?.clear()
                        binding.etEmailSupplierSignUp.text?.clear()
                    } else {
                        binding.supplierSignupBtn.isEnabled = true
                        binding.supplierSignupBtn.text = "Register"
                        binding.supplierRegistrationProgressBar.visibility = View.GONE
                        Log.d(TAG, "onViewCreated: ${it.message.toString()}")
                        Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_LONG).setAnchorView(binding.supplierSignupBtn).show()
                    }
                }
            }
        }
    }

    private fun clearErrors() {
        if (Errors.isEmpty()) return
        for (err in Errors){
            err.error = null
        }
    }


    private fun setError(view: TextInputLayout, errorMessage:String) {
        view.error = "* $errorMessage"
        Errors.add(view)
    }

    private fun showSignUpButton() {
        binding.supplierSignupBtn.isEnabled = true
        binding.supplierSignupBtn.text = "Register"
        binding.supplierRegistrationProgressBar.visibility = View.GONE
    }

    private fun dropDown() {
        val gender = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(requireContext(), R.layout.gender_list, gender)
        with(binding.sexAutoTextView) {
            setAdapter(adapter)
        }
    }

    private fun companyDropDown() {
        val xyz = companiesById.keys.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_of_companies, xyz)
        with(binding.companyAutoTextView) {
            setAdapter(adapter)
        }
        binding.companyAutoTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                binding.etCompanyId.setText(companiesById.getValue(parent?.getItemAtPosition(position)
                    .toString()))
            }
    }
}