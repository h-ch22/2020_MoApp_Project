package hitesh.asimplegame;

public class SendtoServer {
    public String phone, gender, email;

    public void setEmail(String email){
        this.email = email;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public String getGender(){
        return gender;
    }

    public String getPhone(){
        return phone;
    }
}
