package home_works.lesson_2_Exceptions;

public class HomeWork_lesson2 {
    public static void main(String[] args){
        String[][] arr = {{"1", "3", "15", "10"},
                {"7", "11", "5", "1"},
                {"1", "23", "6", "2"},
                {"3", "4", "5", "6"},
        };


        try {
            System.out.println("Сумма элементов массива: "+ arraySum(arr));
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }


    private static int arraySum(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length != 4) {
            throw new MyArraySizeException("Размер внешнего массива !=4");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].length != 4) {
                throw new MyArraySizeException("размер внутреннего массива с индексом [" + i + "]!=4");
            }
        }
        
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    result += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Элемент массива с индексом[" + i + "][" + j + "] (\"" + array[i][j] + "\")- не число");
                }
            }
        }
        return result;
    }
}
