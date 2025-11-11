import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class Sort extends JPanel {
int[] array;
int arraySize = 60;
private final int delay = 50;
String CurrAlgo = "Bubble Sort";
int currIndex = -1;
int comparisons = 0;
private JSlider slider;
private JLabel label;
private JTextArea info;
private String PseudoCode;
public void genArray() {
    array = new int[arraySize];
    if (arraySize <= 10) {
            int response = JOptionPane.showConfirmDialog(
            this,
            "Array size is " + arraySize + ". Do you want to enter the values manually?",
            "Manual Input",
            JOptionPane.YES_NO_OPTION
);
    if (response == JOptionPane.YES_OPTION) {
        for (int i = 0; i < arraySize; i++) {
        while (true) {
    String input = JOptionPane.showInputDialog(this, "Enter value " + (i+1) + " (between 50 and 550):");
         try {
         int val = Integer.parseInt(input);
         if (val >= 50 && val <= 550) {
         array[i] = val;
         break;
    } else {
    JOptionPane.showMessageDialog(this, "Please enter a value between 50 and 550.");
    }
       } catch (NumberFormatException e) {
JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.");
     }
     }
  }
   currIndex = -1;
   return;
   }
    }
        Random r = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = r.nextInt(500) + 50;
        }
        currIndex = -1;
    }

    public void PseudoCode() {
        switch (CurrAlgo) {
            case "Bubble Sort":
                PseudoCode = "Bubble Sort:\nO(n²) Avg/Worst, O(n) Best\n1. Compare adjacent elements.\n2. Swap if needed.\n3. Repeat.\n";
                break;
            case "Insertion Sort":
                PseudoCode = "Insertion Sort:\nO(n²) Avg/Worst, O(n) Best\n1. Pick next element.\n2. Compare & insert.\n";
                break;
            case "Selection Sort":
                PseudoCode = "Selection Sort:\nO(n²) Avg/Worst/Best\n1. Find min in unsorted part.\n2. Swap with first unsorted.\n";
                break;
            case "Merge Sort":
                PseudoCode = "Merge Sort:\nO(n log n) Avg/Worst/Best\n1. Divide & conquer.\n2. Sort halves.\n3. Merge.\n";
                break;
            case "Quick Sort":
                PseudoCode = "Quick Sort:\nO(n log n) Avg/Best, O(n²) Worst\n1. Choose pivot.\n2. Partition.\n3. Recur.\n";
                break;
            case "Counting Sort":
                PseudoCode = "Counting Sort:\nO(n + k)\nStable if done carefully\n1. Count frequency.\n2. Modify count.\n3. Build output.\n";
                break;
            case "Radix Sort":
                PseudoCode = "Radix Sort:\nO(nk) where k = no. of digits\n1. Sort by each digit (LSD first).\n2. Use stable sort like Counting Sort.\n";
                break;
            default:
                PseudoCode = "Select a sorting algorithm.";
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int barWidth = width/arraySize;
        for (int i = 0; i < array.length; i++) {
            int barHeight = array[i];
            int x = i * barWidth;
            int y = height - barHeight;
            g.setColor(i == currIndex ? Color.RED : Color.BLACK);
            g.fillRect(x, y, barWidth, barHeight);
            String valueStr = String.valueOf(array[i]);
            int fontSize = 20;
            Font font;
            FontMetrics fm;
            int strWidth;
            do {
                font = new Font("Monospaced", Font.PLAIN, fontSize);
                fm = g.getFontMetrics(font);
                strWidth = fm.stringWidth(valueStr);
                fontSize--;
            } while (strWidth > barWidth && fontSize > 4);
            fontSize++;
            font = new Font("Monospaced", Font.PLAIN, fontSize);
            fm = g.getFontMetrics(font);
            strWidth = fm.stringWidth(valueStr);
            int strX = x + (barWidth - strWidth) / 2;
            int strY = y - 3;
            if (strY - fm.getAscent() < 0) {
                strY = y + fm.getAscent() + 3;
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.setFont(font);
            g.drawString(valueStr, strX, strY);
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(x, 0, x, height);
        }
    }
    public void pause() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void swap(int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    private void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++)
            for (int j = 0; j < array.length - i - 1; j++) {
                currIndex = j;
                comparisons++;
                if (array[j] > array[j + 1]) swap(j, j + 1);
                repaint(); pause();
            }
        currIndex = -1;
        repaint();
    }

    private void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                currIndex = j;
                comparisons++;
                if (array[j] < array[minIndex]) minIndex = j;
                repaint();
                pause();
            }

            swap(i, minIndex);
            currIndex = i;
            repaint();
            pause();
        }
        currIndex = -1;
        repaint();
    }

    private void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i], j = i - 1;
            while (j >= 0 && array[j] > key) {
                comparisons++;
                array[j + 1] = array[j];
                currIndex = j; j--;
                repaint(); pause();
            }
            array[j + 1] = key;
            repaint(); pause();
        }
        currIndex = -1;
    }

    private void mergeSort(int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(l, m); mergeSort(m + 1, r);
            merge(l, m, r);
        }
    }

    private void merge(int l, int m, int r) {
        int[] L = Arrays.copyOfRange(array, l, m + 1);
        int[] R = Arrays.copyOfRange(array, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < L.length && j < R.length) {
            currIndex = k;
            comparisons++;
            array[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
            repaint(); pause();
        }
        while (i < L.length) {
            currIndex = k;
            array[k++] = L[i++];
            repaint(); pause();
        }
        while (j < R.length) {
            currIndex = k;
            array[k++] = R[j++];
            repaint(); pause();
        }
    }
    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low;

        for (int j = low; j < high; j++) {
            currIndex = j;
            comparisons++;
            if (array[j] <= pivot) {
                swap(i, j);
                i++;
            }
            repaint();
            pause();
        }

        swap(i, high);
        currIndex = i;
        repaint();
        pause();
        return i;
    }

    private void countingSort() {
        int max = Arrays.stream(array).max().getAsInt();
        int[] count = new int[max + 1];
        for (int val : array) count[val]++;
        int index = 0;
        for (int i = 0; i <= max; i++)
            while (count[i]-- > 0) {
                array[index++] = i; currIndex = index - 1;
                repaint(); pause();
            }
    }

    private void radixSort() {
        int max = Arrays.stream(array).max().getAsInt();
        for (int exp = 1; max / exp > 0; exp *= 10) countingSortForRadix(exp);
    }

    private void countingSortForRadix(int exp) {
        int[] output = new int[array.length];
        int[] count = new int[10];
        for (int val : array) count[(val / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];
        for (int i = array.length - 1; i >= 0; i--)
            output[--count[(array[i] / exp) % 10]] = array[i];
        for (int i = 0; i < array.length; i++) {
            array[i] = output[i]; currIndex = i;
            repaint(); pause();
        }
    }

    public Sort() {
        genArray();
        JFrame frame = new JFrame("Sorting Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        this.setBackground(Color.WHITE);
        frame.add(this, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.DARK_GRAY);
        JButton start = new JButton("Start");
        JButton reset = new JButton("Reset");
        String[] algorithms = {
                "Bubble Sort", "Insertion Sort", "Selection Sort",
                "Merge Sort", "Quick Sort", "Counting Sort",
                "Radix Sort"
        };
        JComboBox<String> algoSlec = new JComboBox<>(algorithms);
        btnPanel.add(start); btnPanel.add(reset); btnPanel.add(algoSlec);

        slider = new JSlider(10, 100, arraySize);
        label = new JLabel("Array Size: " + arraySize);
        slider.setMajorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(Color.DARK_GRAY);
        slider.setForeground(Color.WHITE);
        btnPanel.add(slider); btnPanel.add(label);

        info = new JTextArea(10, 30);
        info.setBackground(Color.DARK_GRAY);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Monospace", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(info);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        start.addActionListener(e -> new Thread(() -> {
            comparisons = 0;
            info.setText("Sorting using: " + CurrAlgo + "\n" + PseudoCode + "\n");
            switch (CurrAlgo) {
                case "Bubble Sort": bubbleSort(); break;
                case "Selection Sort": selectionSort(); break;
                case "Insertion Sort": insertionSort(); break;
                case "Merge Sort": mergeSort(0, array.length - 1); break;
                case "Quick Sort": quickSort(0, array.length - 1); break;
                case "Counting Sort": countingSort(); break;
                case "Radix Sort": radixSort(); break;
            }
            info.append("Sorting completed!\nComparisons: " + comparisons + "\n");
        }).start());

        reset.addActionListener(e -> {
            genArray(); repaint();
            info.setText("Array reset. Choose a sorting algorithm and start.\n");
        });

        algoSlec.addActionListener(e -> {
            CurrAlgo = (String) algoSlec.getSelectedItem();
            PseudoCode();
            info.setText("Current Algorithm: " + CurrAlgo + "\n" + PseudoCode + "\n");
        });

        slider.addChangeListener(e -> {
            arraySize = slider.getValue();
            label.setText("Array Size: " + arraySize);
            genArray(); repaint();
            info.setText("Array size updated to: " + arraySize + "\n");
        });

        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.add(scroll, BorderLayout.EAST);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sort::new);
    }
}
