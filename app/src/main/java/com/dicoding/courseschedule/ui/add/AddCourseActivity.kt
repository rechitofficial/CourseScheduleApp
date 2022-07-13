package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListViewModel
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    companion object {
        private const val START_TIME = "START TIME"
        private const val END_TIME = "END TIME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<TextInputEditText>(R.id.add_course_name).text.toString()
                val day = findViewById<Spinner>(R.id.dropdown_day).selectedItemPosition
                val startTime = findViewById<TextView>(R.id.tv_start_clock).text.toString()
                val endTime = findViewById<TextView>(R.id.tv_end_clock).text.toString()
                val lecturer = findViewById<TextInputEditText>(R.id.add_lecturer_name).text.toString()
                val note = findViewById<TextInputEditText>(R.id.add_note).text.toString()
                viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                viewModel.saved.observe(this) {
                    if(it.getContentIfNotHandled() == true){
                        Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        // Siapkan time formatter-nya
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        // Set text dari textview berdasarkan parameter tag
        when(tag) {
            START_TIME -> findViewById<TextView>(R.id.tv_start_clock).text = timeFormat.format(calendar.time)
            END_TIME -> findViewById<TextView>(R.id.tv_end_clock).text = timeFormat.format(calendar.time)
            else -> {
            }
        }
    }

    fun showTimePicker(view: View) {
        val timePickerFragment = TimePickerFragment()
        when(view.id){
            R.id.add_start_time_picker -> {
                timePickerFragment.show(supportFragmentManager, START_TIME)
            }
            R.id.add_end_time_picker -> {
                timePickerFragment.show(supportFragmentManager, END_TIME)
            }
        }
    }
}