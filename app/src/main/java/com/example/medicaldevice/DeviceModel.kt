package com.example.medicaldevice

class DeviceModel(name: String?, telephone: String?, email: String?) {
    private var name: String = name!!
    private var telephone: String = telephone!!
    private var email: String = email!!

    fun getName(): String?{
        return this.name
    }

    fun setName(name: String?){
        this.name = name!!
    }

    fun getTelephone(): String?{
        return this.telephone
    }

    fun setTelephone(telephone: String?){
        this.telephone = telephone!!
    }

    fun getEmail(): String?{
        return this.email
    }

    fun setEmail(email: String?){
        this.email = email!!
    }

}