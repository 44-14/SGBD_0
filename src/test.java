import com.main.schoolux.validations.CommonValidation;

public class test {

    public static void main(String[] args){

        // clic droit sur main puis Run 'test.main()'

        System.out.println("Hello World");

        int id = 3333;
        String test = null ;


        try
        {
            id = Integer.parseInt(test);

        }catch (NumberFormatException e){
            System.out.println(e);
            System.out.println(id);

        }

        id = CommonValidation.checkValid_Id(test);
        System.out.println(id);





    }
}
