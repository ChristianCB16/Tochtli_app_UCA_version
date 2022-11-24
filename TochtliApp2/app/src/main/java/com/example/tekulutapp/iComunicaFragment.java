package com.example.tekulutapp;

import com.example.tekulutapp.Models.Curso;
import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.Models.Modulo;

public interface iComunicaFragment {
    void enviarCurso(Curso cursito);

    void enviarModulo(Modulo modulito);

    void enviarLeccion(Leccion leccionsita);
    //---------------------------------------------------------
    void enviarCursoVideo(Curso cursito);

    void enviarModuloVideo(Modulo modulito);

    void enviarLeccionVideo(Leccion leccionsita);
    //---------------------------------------------------------------------

    void enviarQuizCurso(Curso cursito);

    void enviarModuloQuiz(Modulo modulito);

    void enviarLeccionQuiz(Leccion leccionsita);

    //-----------------------------------------------------------
    void enviarAudioCurso(Curso cursito);
    void enviarAudioModulo(Modulo modulito);

    void enviarAudioLeccion(Leccion leccionsita);


}
