package Main;

public class Student extends User{
    String fName;
    String lName;
    int studentId;

    public Student(String fname, String lname, int stid){
        this.fName = fname;
        this.lName = lname;
        this.studentId = stid;
        this.setLoginID(id);
 +      this.setLoginPW(pw);
 +      this.setType('s');
 +      Data.AddUser(id,pw);
    }

    String getName(){
        return this.fName+" "+this.lName;
    }

    int getID(){
        return this.studentId;
    }

}
