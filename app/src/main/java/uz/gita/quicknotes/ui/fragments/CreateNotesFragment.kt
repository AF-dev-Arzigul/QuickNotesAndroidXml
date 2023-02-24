package uz.gita.quicknotes.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.chinalwb.are.AREditText
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import com.chinalwb.are.styles.toolbar.IARE_Toolbar
import com.chinalwb.are.styles.toolitems.*
import uz.gita.quicknotes.model.Notes
import uz.gita.quicknotes.R
import uz.gita.quicknotes.viewModel.NotesViewModel
import uz.gita.quicknotes.databinding.FragmentCreateNotesBinding
import java.util.*

class CreateNotesFragment : Fragment() {
    lateinit var binding: FragmentCreateNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    var priority: String = "1"
    private lateinit var mToolbar: IARE_Toolbar
    private lateinit var mEditText: AREditText
    private var scrollerAtEnd = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.pGreen.setImageResource(R.drawable.ic_baseline_check_24)

        binding.pGreen.setOnClickListener {
            binding.pGreen.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
            priority = "1"
        }

        binding.pYellow.setOnClickListener {
            binding.pYellow.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
            priority = "2"
        }

        binding.pRed.setOnClickListener {
            binding.pRed.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
            priority = "3"
        }

        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitleCreate.html
        val notes = binding.etNotes.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        if (title.isNotEmpty() && subTitle.isNotEmpty()) {
            val data = Notes(null, title, subTitle, notes, notesDate.toString(), priority)
            viewModel.addNotes(data)
            Toast.makeText(requireContext(), "Note created successfully", Toast.LENGTH_SHORT)
                .show()
            Navigation.findNavController(it!!).navigateUp()
        } else {
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {

            mToolbar = findViewById(R.id.areToolbar)
            val bold: IARE_ToolItem = ARE_ToolItem_Bold()
            val italic: IARE_ToolItem = ARE_ToolItem_Italic()
            val underline: IARE_ToolItem = ARE_ToolItem_Underline()
            val strikethrough: IARE_ToolItem = ARE_ToolItem_Strikethrough()
            val quote: IARE_ToolItem = ARE_ToolItem_Quote()
            val listNumber: IARE_ToolItem = ARE_ToolItem_ListNumber()
            val listBullet: IARE_ToolItem = ARE_ToolItem_ListBullet()
            val hr: IARE_ToolItem = ARE_ToolItem_Hr()
            val link: IARE_ToolItem = ARE_ToolItem_Link()
            val subscript: IARE_ToolItem = ARE_ToolItem_Subscript()
            val superscript: IARE_ToolItem = ARE_ToolItem_Superscript()
            val left: IARE_ToolItem = ARE_ToolItem_AlignmentLeft()
            val center: IARE_ToolItem = ARE_ToolItem_AlignmentCenter()
            val right: IARE_ToolItem = ARE_ToolItem_AlignmentRight()
            val image: IARE_ToolItem = ARE_ToolItem_Image()
            val video: IARE_ToolItem = ARE_ToolItem_Video()
            val at: IARE_ToolItem = ARE_ToolItem_At()
            mToolbar.addToolbarItem(bold)
            mToolbar.addToolbarItem(italic)
            mToolbar.addToolbarItem(underline)
            mToolbar.addToolbarItem(strikethrough)
            mToolbar.addToolbarItem(quote)
            mToolbar.addToolbarItem(listNumber)
            mToolbar.addToolbarItem(listBullet)
            mToolbar.addToolbarItem(hr)
            mToolbar.addToolbarItem(link)
            mToolbar.addToolbarItem(subscript)
            mToolbar.addToolbarItem(superscript)
            mToolbar.addToolbarItem(left)
            mToolbar.addToolbarItem(center)
            mToolbar.addToolbarItem(right)
            mToolbar.addToolbarItem(image)
            mToolbar.addToolbarItem(video)
            mToolbar.addToolbarItem(at)

            mEditText = findViewById(R.id.et_subtitle_create)
            mEditText.setToolbar(mToolbar)

            val imageView: ImageView = findViewById(R.id.arrow1)
            if (mToolbar is ARE_ToolbarDefault) {
                (mToolbar as ARE_ToolbarDefault).viewTreeObserver.addOnScrollChangedListener {
                    val scrollX = (mToolbar as ARE_ToolbarDefault).scrollX
                    val scrollWidth = (mToolbar as ARE_ToolbarDefault).width
                    val fullWidth = (mToolbar as ARE_ToolbarDefault).getChildAt(0).width
                    scrollerAtEnd = if (scrollX + scrollWidth < fullWidth) {
                        imageView.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
                        false
                    } else {
                        imageView.setImageResource(R.drawable.ic_baseline_arrow_back_24)
                        true
                    }
                }
            }

            imageView.setOnClickListener {
                scrollerAtEnd = if (scrollerAtEnd) {
                    (mToolbar as ARE_ToolbarDefault?)!!.smoothScrollBy(-Int.MAX_VALUE, 0)
                    false
                } else {
                    val hsWidth = (mToolbar as ARE_ToolbarDefault?)!!.getChildAt(0).width
                    (mToolbar as ARE_ToolbarDefault?)!!.smoothScrollBy(hsWidth, 0)
                    true
                }
            }
        }
    }
}