package pp.developer.ejercicio04f.ui.home

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
import pp.developer.ejercicio04f.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    //private var _binding: FragmentHomeBinding? = null
    //Valida usuario autenticado
    private  lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

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
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val u= view.findViewById<EditText>(R.id.iTxtUsuario)//.text.toString()
        val p= view.findViewById<EditText>(R.id.iTxtContrasena)//.text.toString()
        val btn= view.findViewById<Button>(R.id.btnLogin)
        val lbl= view.findViewById<TextView>(R.id.txtVRegistro)

        btn.setOnClickListener {
            var usr = u.text.toString()
            var pswd = p.text.toString()
            if( !usr.isNullOrEmpty() && !pswd.isNullOrEmpty() ){
                auth.signInWithEmailAndPassword(u.text.toString(),p.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_slideshow)
                    }else Message("Datos incorrectos")

                }.addOnFailureListener {
                    Message(it.localizedMessage)
                }
            }else{
                Message("Datos requeridos.")
            }
        }

        lbl.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_gallery)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    fun validateEmail(email: String): Boolean {
        val pattern: String = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+\$"
        val regExp: Regex = Regex(pattern)
        return regExp.matches(email)
    }
    private fun Message( message:String){
        Toast.makeText(context,"${message}", Toast.LENGTH_LONG).show()
    }
}