package uz.gita.quicknotes.ui.fragments

import android.os.Bundle
import android.text.Html
import android.text.format.DateFormat
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chinalwb.are.AREditText
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import com.chinalwb.are.styles.toolbar.IARE_Toolbar
import com.chinalwb.are.styles.toolitems.*
import uz.gita.quicknotes.model.Notes
import uz.gita.quicknotes.R
import uz.gita.quicknotes.viewModel.NotesViewModel
import uz.gita.quicknotes.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment() {

    private val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding: FragmentEditNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var mToolbar: IARE_Toolbar
    private lateinit var mEditText: AREditText
    var priority: String = "1"
    private var scrollerAtEnd = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)

        binding.etTitleEdit.setText(oldNotes.data.title)
        binding.etSubtitleEdit.setText(Html.fromHtml(oldNotes.data.subTitle))
        binding.etNotes.setText(oldNotes.data.notes)

        if (oldNotes.data.priority == "1") {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        } else if (oldNotes.data.priority == "2") {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
        } else {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_baseline_check_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

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

        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }
        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.etTitleEdit.text.toString()
        val subTitle = binding.etSubtitleEdit.html
        val notes = binding.etNotes.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        if (title.isNotEmpty() && subTitle.isNotEmpty()) {
            val data =
                Notes(oldNotes.data.id, title, subTitle, notes, notesDate.toString(), priority)
            viewModel.updateNotes(data)
            Toast.makeText(requireContext(), "Notes updated successfully", Toast.LENGTH_SHORT)
                .show()
            Navigation.findNavController(it!!).navigateUp()
        } else {
            Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            val bottomSheet =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data!!)
                bottomSheet.dismiss()
                Navigation.findNavController(binding.btnEditSaveNotes)
                    .navigate(R.id.action_editNotesFragment_to_homeFragment)

            }
            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        } else {
            findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.apply {

            mToolbar = findViewById(R.id.areToolbarEdit)
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

            mEditText = findViewById(R.id.et_subtitle_edit)
            mEditText.setToolbar(mToolbar)

            val imageView: ImageView = findViewById(R.id.arrow)
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