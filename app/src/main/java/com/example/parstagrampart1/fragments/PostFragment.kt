package com.example.parstagrampart1.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import com.example.parstagrampart1.MainActivity
import com.example.parstagrampart1.Post
import com.example.parstagrampart1.R
import com.parse.ManifestInfo
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [PostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val photoFileName = "photo.jpg"
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        val save: Button = view.findViewById(R.id.send)
        val photo: Button = view.findViewById(R.id.photo)
        save.setOnClickListener() {
            val description = view.findViewById<TextView>(R.id.description).text.toString()
            val user = ParseUser.getCurrentUser()
            var file = MainActivity().getPhotoFileUri(photoFileName)

            submitPost(description, user, file)

        }
        photo.setOnClickListener() {
            (activity as MainActivity).onLaunchCamera()
        }
        return view
    }



    private fun submitPost(description: String, user: ParseUser, file: File) {
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground{exception ->
            if (exception != null) {
                Log.e(MainActivity.TAG, "Error while saving post.")
                exception.printStackTrace()
            } else {
                Log.i(MainActivity.TAG, "Successfully uploaded")
                Toast.makeText(context, "Successfully uploaded.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}