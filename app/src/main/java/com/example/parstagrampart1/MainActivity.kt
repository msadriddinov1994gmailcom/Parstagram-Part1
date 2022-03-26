package com.example.parstagrampart1

import android.accounts.AuthenticatorDescription
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.parstagrampart1.fragments.HomeFragment
import com.example.parstagrampart1.fragments.PostFragment
import com.example.parstagrampart1.fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File

private val homeFragment = HomeFragment()
private val postFragment = PostFragment()
private val userFragment = UserFragment()



class MainActivity : AppCompatActivity() {

    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    val photoFileName = "photo.jpg"
    var photoFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queryPosts()

        findViewById<Button>(R.id.btn_submit).setOnClickListener() {
            val description = findViewById<EditText>(R.id.description).text.toString()
            val user = ParseUser.getCurrentUser()
            if (photoFile != null) {
                submitPost(description, user, photoFile!!)
                Log.i(TAG, "Successfully uploaded")
                Toast.makeText(this, "Successfully uploaded.", Toast.LENGTH_SHORT).show()
                clear()
            } else {
                Log.e(TAG, "Error while posting.")
                Toast.makeText(this, "Error while posting.", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.btn_photo).setOnClickListener() {
            onLaunchCamera()
        }
        findViewById<Button>(R.id.btn_Timeline).setOnClickListener() {
            gotToTimeline()
        }


    }

    private fun gotToTimeline() {
        val intent = Intent(this, TimelineActivity::class.java)
        startActivityForResult(intent, 12)
    }

    private fun submitPost(description: String, user: ParseUser, file: File) {
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground{exception ->
            if (exception != null) {
                Log.e(TAG, "Error while saving post.")
                exception.printStackTrace()
            } else {
                Log.i(TAG, "Successfully uploaded")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                val ivPreview: ImageView = findViewById(R.id.imageView)
                ivPreview.setImageBitmap(takenImage)
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clear() {
        val editText: EditText = findViewById(R.id.description)
        val imageView: ImageView = findViewById(R.id.imageView)
        editText.setText("")
        imageView.setImageResource(R.drawable.placeholder)
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    private fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.findInBackground(object : FindCallback<Post>{
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching posts.")
                } else {
                    if (posts != null) {
                        for (post in posts){
                            Log.e(TAG, "Post: " + post.getDescription())
                        }
                    }
                }
            }

        })
    }
    companion object {
        const val TAG = "MainActivity"
    }

}