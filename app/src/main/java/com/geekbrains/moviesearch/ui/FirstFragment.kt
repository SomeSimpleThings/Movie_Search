package com.geekbrains.moviesearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.vo.Movie
import com.geekbrains.moviesearch.vo.MovieCopier
import java.lang.StringBuilder

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.textview_first)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            textView.text = Movie(1, "Star Wars").toString()
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            textView.text = MovieCopier.copyMovie(Movie(2, "Harry Potter")).toString()
        }

        view.findViewById<Button>(R.id.button_third).setOnClickListener {
            val moviesArray = arrayOf(
                Movie(1, "Movie 1"),
                Movie(2, "Movie 2"),
                Movie(3, "Movie 3")
            )

            val stringBuilder = StringBuilder()
            for (i in moviesArray.indices) {
                stringBuilder.append(moviesArray[i].toString()).append("\n")
            }
            for (i in 100 downTo 0 step 50) {
                Toast.makeText(context, i.toString(), Toast.LENGTH_SHORT).show()
            }

            textView.text = stringBuilder.toString()
        }
    }
}