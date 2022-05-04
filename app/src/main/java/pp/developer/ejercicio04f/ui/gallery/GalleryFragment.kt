package pp.developer.ejercicio04f.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pp.developer.ejercicio04f.R
import pp.developer.ejercicio04f.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

//    private var _binding: FragmentGalleryBinding? = null
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!

    //Valida usuario autenticado
    private  lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val usr: FirebaseUser? = auth.currentUser
        if(usr != null){
            //Navigation.findNavController()
        }else{
            Message("usuario no valido. Favor de ingresar.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_gallery,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nom = view.findViewById<EditText>(R.id.eTxtNombre)
        var ap = view.findViewById<EditText>(R.id.eTxtApellido)
        var em = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        var pw = view.findViewById<EditText>(R.id.editTextTextPassword)
        val btnGuarda = view.findViewById<Button>(R.id.btnGuarda)
        var email:String = ""
        var pswd:String = ""
        var n:String = ""
        var a:String = ""
        btnGuarda.setOnClickListener {
            email = em.text.toString()
            pswd = pw.text.toString()
            n = nom.text.toString()
            a = ap.text.toString()
            if(!n.isNullOrEmpty() && !a.isNullOrEmpty() && !email.isNullOrEmpty() && !pswd.isNullOrEmpty()){
                auth.createUserWithEmailAndPassword(email,pswd).addOnCompleteListener {
                    if(it.isSuccessful){
                        Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_nav_home)
                    }//else Message(it.result.toString())
                }.addOnFailureListener {
                    Message(it.localizedMessage)
                }
            }else Message("Llene formulario")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
    private fun Message( message:String){
        Toast.makeText(context,"${message}", Toast.LENGTH_LONG).show()
    }
}