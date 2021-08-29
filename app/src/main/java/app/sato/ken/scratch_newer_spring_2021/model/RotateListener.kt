package app.sato.ken.scratch_newer_spring_2021.model

interface RotateListener {
    fun onRotateStart()
    fun onRotateEnd(result: String)
}