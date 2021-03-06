import java.util.Arrays;

public class Sorts {

    private static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private void BubleSort(int[] array) { // Сортировка пузырьком, будем проходится до тех пора пока не отсортируется
        boolean needIteration = true;
        while (needIteration) {
            needIteration = true;
            for (int i = 1; i < array.length; i++ ) {
                if (array[i] < array[i-1]) {
                    swap(array, i, i-1);
                    needIteration = true;
                }
            }
        }
    }

    // Сортировка выбором(selection sort)
//    Другая сортировка — сортировка выбором. Она также имеет квадратичную сложность, но об этом чуть позже.
//
//            Итак, идея простая. Каждый проход выбирать самый минимальный элемент и смещать его в начало. При этом каждый новый проход начинать сдвигаясь вправо,
//              то есть первый проход — с первого элемента, второй проход — со второго. Выглядеть это будет следующим образом:
//

    private void SelectionSort(int[] array) {
        for (int left = 0; left < array.length; left++ ) {
            int minInd = left;
            for (int i = left; i < array.length; i++ ) {
                if (array[i] < array[minInd]) {
                    minInd = i;
                }
            }
            swap(array, left, minInd);
        }
    }

//    Сортировка вставками тоже имеет квадратичную сложность, так как у нас опять цикл в цикле.
//    В чём отличие от сортировки выбором? Данная сортировка является "устойчивой".
//    Это значит, что одинаковые элементы не изменят свой порядок. Одинаковые с точки зрения характеристики, по которой мы сортируем.

    private void InsertionSort(int[] array) {
        for (int left = 0; left < array.length; left++ ) {
            // Вытаскиваем значение аргумента
            int value = array[left];
            // Перемещаемся по элементам, которые пенред вытащенном элементом
            int i = left - 1;
            for (; i>= 0; i--) {
                // Если вытащили значение меньше передвигаем большой элемент дальше
                if (value < array[i]) {
                    array[i + 1] = array[i];
                } else {
                    // Если вытащенный жлемент больше, стоп
                    break;
                }

            }
            // В освободившиеся место вставляем вытащенное значение
            array[i + 1 ] = value;
        }
    }


    private void ShuttleSort(int[] array) {
        for (int i = 1; i < array.length; i++ ) {
            if (array[i] < array[i - 1]) {
                swap(array, i, i -1 );
                for (int z = i - 1; (z - 1) >= 0; z-- ) {
                    if (array[z] < array[z - 1]) {
                        swap(array, z, z-1);
                    } else {
                        break;
                    }
                }
            }
        }
    }


    private void ShellSort(int[] array) {
        int gap = array.length / 2;

        while (gap >= 1) {
            System.out.println(gap);
            for (int right = 0; right < array.length; right++ ) {

                for (int c = right - gap; c >= 0; c -= gap) {
                    if (array[c] > array[c + gap]) {
                        swap(array, c, c + gap);
                        System.out.println(Arrays.toString(array) + " right= " + right + " c= " + c + " gap = " + gap);
                    }
                }
            }
            gap = gap / 2;
        }
        System.out.println(Arrays.toString(array));
    }
    // Первый массив src1Start с какого элемента первого массива начинать, так же и со 2 массивом, dest - массив приемник, и с какого начинать элемента,

    private static void merge(int[] src1, int src1Start, int[] src2, int src2Start, int[] dest, int destStart, int size) {
        int index1 = src1Start;
        int index2 = src2Start;

        int src1End = Math.min(src1Start + size, src1.length);
        int src2End = Math.min(src2Start + size, src2.length);

        int iterationCount = src1End - src1Start + src2End - src2Start; // количество итераций

        for (int i = destStart; i < destStart + iterationCount; i++ ) {
            if (index1 < src1End && (index2 >= src2End || src1[index1] < src2[index2])) {
                dest[i] = src1[index1];
                index1++;
            } else {
                dest[i] = src2[index2];
                index2++;
            }
        }
    }

    private static int[] mergeSort(int[] array) {
        int[] tmp; // Временная массив
        int[] currentSrc = array; // массив источник
        int[] currentDest = new int[array.length]; // приемник, куда мы результат заливаем

        int size = 1; // Размер массивов
        while (size < array.length) {
            for (int i = 0; i < array.length; i += 2 * size) {
                merge(currentSrc, i, currentSrc, i + size, currentDest, i , size);
            }

            tmp = currentSrc;
            currentSrc = currentDest;
            currentDest = tmp;

            size = size * 2;

            System.out.println(arrayToString(currentSrc));
        }
        return currentSrc;
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        sb.append("]");
        return sb.toString();
    }


    public static void quickSort(int[] array, int from, int to) {
        if (from < to) {
            // Опорный элемент
           int divideIndex = partition(array, from, to);
            // Выведем массив
            printSortStep(array, from, to, divideIndex);
            // Сортируем подмассив левой части
           quickSort(array, from, divideIndex - 1);
            // Сортируем подмассив правой части
           quickSort(array, divideIndex, to);
        }

    }

    private static int partition(int[] array, int from, int to) {
        int leftIndex = from;
        int rightIndex = to;

        int pivot = array[from];

        while (leftIndex <= rightIndex) {
            while (array[leftIndex] < pivot) {
                leftIndex++;
            }

            while (array[rightIndex] > pivot) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(array, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }

        return leftIndex;
    }

    private static void printSortStep(int[] arr, int from, int to, int partitionIndex) {
        System.out.print(arrayToString(arr));
        System.out.print("\npartition at index: " + partitionIndex);
        System.out.print(", left: " + arrayToString(Arrays.copyOfRange(arr, from, partitionIndex)));
        System.out.println(", right: " + arrayToString(Arrays.copyOfRange(arr, partitionIndex, to + 1)) + "\n");
    }





    public static int searchLiner(int[] array, int elementToFind) {
        for (int i = 0; i < array.length; i++) {
            if (elementToFind == array[i]) {
                return i;
            }
        }
        return - 1;
    }


    public static int binarySearch(int[] sortedArray, int key, int low, int high) {
        if (high >= low) {
            int middle = low + (high - low) / 2;

            if (sortedArray[middle] == key) {
                return middle;
            }

            if (sortedArray[middle] > key) {
                return binarySearch(sortedArray, key, low, middle - 1);
            } else {
                return binarySearch(sortedArray, key, middle + 1, high);
            }
        }

        return -1;
    }

    public static int searchBinary(int[] sortedArray, int key, int low, int high) {
        int startIndex = 0;
        int middle;
        int endIndex = sortedArray.length - 1;

        while (startIndex <= endIndex) {
            middle = startIndex + (endIndex - startIndex) / 2;

            if (sortedArray[middle] == key) {
                return middle;
            }
            if (sortedArray[middle] > key) {
                endIndex = middle - 1;
            } else {
                startIndex = middle + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {10, 2, 10, 3, 1, 0, 12, 8};
        Sorts a = new Sorts();

        Sorts.quickSort(array, 0 , array.length- 1);

    }



}
