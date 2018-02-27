import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class ShoppingCartSystem extends JFrame {

    /**
  * 
  */
 private static final long serialVersionUID = 1L;
 private JPanel sourceListPanel;
    private JPanel shoppingCartPanel;
    private JPanel buttonsPanel;

    
    private JList sourceList;
    private JList shoppingCart;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;

    private JButton addButton;
    private JButton removeButton;
    private JButton checkListButton;

   
    private  double []quantity;
    private String []books;
    private double []price;
    private String []cart;
    private int cartSize;
    private double subTotal = 0.0,tax,total;

    ShoppingCartSystem(){
        setTitle("Shopping Cart System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        quantity = new double[40];
        books = new String[40];
        price = new double[40];
        cart = new String[40];
        cartSize = 0;
       
        readDataFromFile();
        for(int i = 0; i < 4; i++){
        System.out.println("quantity: " + quantity[i]);
        System.out.println("price: " + price[i]);
        }
        buildSourceListPanel();
        buildShoppingCartPanel();
        buildButtonsPanel();

        add(sourceListPanel,BorderLayout.WEST);
        add(shoppingCartPanel,BorderLayout.EAST);
        add(buttonsPanel,BorderLayout.SOUTH);
        pack();
        setVisible(true);
       
    }

    private void readDataFromFile(){
        try{
            DataInputStream dis = new DataInputStream(new FileInputStream("Books.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            int i=0;
            int j = 0;
            String line;
            while((line = br.readLine()) != null){

                String arr[] = line.split(", ");
                books[i] = arr[0];
                price[i++] = Double.parseDouble(arr[1]);
                quantity[j++] = Double.parseDouble(arr[2]);
               
            }
            br.close();
            dis.close();
        }
        catch(Exception exp){
            System.out.println(exp.toString());
        }
       
    }

    private void buildSourceListPanel(){
     
      
     
        sourceListPanel = new JPanel();
        sourceList = new JList(books);
       
        sourceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sourceList.setVisibleRowCount(6);       
        scrollPane = new JScrollPane(sourceList);
        sourceListPanel.add(scrollPane);
    }

    private void buildShoppingCartPanel(){
        shoppingCartPanel = new JPanel();
        shoppingCart = new JList();       
        shoppingCart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shoppingCart.setVisibleRowCount(6);       
        scrollPane2 = new JScrollPane(shoppingCart);
        shoppingCartPanel.add(scrollPane2);
    }

    private void buildButtonsPanel(){
        buttonsPanel =new JPanel();
        addButton = new JButton("Add to shopping cart");
        removeButton = new JButton("Remove from shopping cart");
        checkListButton =new JButton("Check List");
        buttonsPanel.setLayout(new FlowLayout(10));
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(checkListButton);
        addButton.addActionListener(new ButtonListener());
        removeButton.addActionListener(new ButtonListener());
        checkListButton.addActionListener(new ButtonListener());
    }

    private class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent ae){

            if(ae.getSource() == addButton)
            {
             quantity[sourceList.getSelectedIndex()] = quantity[sourceList.getSelectedIndex()] - 1;
             if(quantity[sourceList.getSelectedIndex()] > -1){
              System.out.println("quantity: " + quantity[1]);
                cart[cartSize++] = (String) sourceList.getSelectedValue();
                shoppingCart.setListData(cart);
                subTotal += price[sourceList.getSelectedIndex()];
             
             }
            }
            else if(ae.getSource() == removeButton)
            {
             quantity[sourceList.getSelectedIndex()] = quantity[sourceList.getSelectedIndex()] + 1;
             System.out.println("quantity: " + quantity[1]);
                for(int i=0;i<books.length;i++)
                    if(shoppingCart.getSelectedValue().equals(books[i]))
                    {
                     
                       subTotal -= price[i];
                       break;
                    }
               
                int selection = shoppingCart.getSelectedIndex();

                for(int i=selection; i< cart.length-1;i++)
                    cart[i] = cart[i+1];
                shoppingCart.setListData(cart);               
            }
            else
            {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                tax = subTotal * 0.06;
                total = subTotal + tax;
                StringBuffer sb = new StringBuffer();
                sb.append("Sub Total: "+df.format(subTotal)+"\n");
                sb.append("Tax: "+df.format(tax)+"\n");
                sb.append("Total: "+df.format(total)+"\n");
                //sb.append("Quantity: "+df.format(quantity[0])+"\n");
                JOptionPane.showMessageDialog(null, sb);
            }
        }
    }
   
    public static void main(String []args){
        new ShoppingCartSystem();
       
    }
}
