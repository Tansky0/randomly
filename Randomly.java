
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Randomly implements ActionListener, Runnable {
	//设置一个标记
	private boolean flag = false;
	private JFrame randomFrame = new JFrame("随机抽取");
	
	// 创建一个Panel对象。
	private JPanel randomPanel = new JPanel();
	//创建一个TextField对象，用于显示文本
	private TextField tf = new TextField(30);
	//创建按钮6 个按钮
	private JButton randomButton1 = new JButton("Run");
	private JButton randomButton2 = new JButton("Stop");
	private JButton randomButton3 = new JButton("Question");
	private JButton randomButton4 = new JButton("Answer");
	private JButton randomButton5 = new JButton("Delete");
	private JButton randomButton6 = new JButton("Number");
	//初始化name
	private List<String> nameage = Arrays.asList("aaa","bbb","ccc");
	ArrayList<String> name = new ArrayList<String>(nameage);
	//取得总的name的长度
	int length = name.size();
	
	HashMap<String, String> answer = new HashMap<String,String>() ;
	

	public Randomly() {
		
		// 向JPanel容器中添加三个组件
		randomPanel.add(tf);
		randomPanel.add(randomButton1);
		randomPanel.add(randomButton2);
		randomPanel.add(randomButton3);
		randomPanel.add(randomButton4);
		randomPanel.add(randomButton5);
		randomPanel.add(randomButton6);
		randomFrame.add(randomPanel);
		
		// 设置组件不可改变大小
		randomFrame.setResizable(false);
		// 设置关闭窗体时结束程序
		randomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置组件大小
		randomFrame.setSize(250, 120);
		// 设置组件位置
		randomFrame.setLocationRelativeTo(null);
		// 设置组件可见
		randomFrame.setVisible(true);
		randomFrame.setLayout(null);
		addListener();
	}
	
	private void addListener() {
		randomButton1.addActionListener(this);
		randomButton2.addActionListener(this);
		randomButton3.addActionListener(this);
		randomButton4.addActionListener(this);
		randomButton5.addActionListener(this);
		randomButton6.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == randomButton1) {
			synchronized (this) {
				notify();
				flag = true;
			}
		}
		if (obj == randomButton2) {
			synchronized (this) {
				flag = false;
			}
		}
		if(obj == randomButton3) {
			Map<String, String> question = new HashMap<String,String>();
			question.put("aaa","aaa的question");
			question.put("bbb","bbb的question");
			question.put("ccc","ccc的question");

			String key = tf.getText();
			JFrame quest = new JFrame(key+"的问题");
			quest.setSize(400, 300);
			//用html标签让文本自动换行
			String que = "<html>"+question.get(key)+"</html>";
			JLabel label = new JLabel(que,JLabel.CENTER);
			label.setFont(new java.awt.Font("Dialog",   1,   15));
			label.setForeground(Color.RED);
			
			quest.add(label);
			quest.setLocationRelativeTo(null);
			quest.setVisible(true);
			
		}
		if(obj == randomButton4) {
			Map<String, String> answer = new HashMap<String,String>();
			answer.put("aaa","aaa的answer");
			answer.put("bbb","bbb的answer");
			answer.put("ccc","ccc的answer");
			String key = tf.getText();
			JFrame quest = new JFrame(key+"的答案");
			quest.setSize(400, 300);
			String ans = "<html>"+answer.get(key)+"</html>";
			JLabel label = new JLabel(ans,JLabel.CENTER);
			label.setSize(500,500);
			label.setFont(new java.awt.Font("Dialog", 1, 15));
			
			quest.add(label);
			quest.setLocationRelativeTo(null);
			quest.setVisible(true);
		}
		if(obj == randomButton5) {
			String key = tf.getText();
			int i = JOptionPane.showConfirmDialog(null, "你确定要删除"+key+"的question吗?", "警告",JOptionPane.YES_NO_OPTION);
			//点击是i为0，否为1
			if(i == 0) {
				name.remove(key);
				length = length -1;
				JOptionPane.showMessageDialog(null, "删除成功，当前还剩"+length+"个question", "提示",JOptionPane.WARNING_MESSAGE); 
			}
		}
		if(obj == randomButton6) {
			JOptionPane.showMessageDialog(null, "当前还剩"+length+"个question","提示",JOptionPane.INFORMATION_MESSAGE); 
			 
		}
	}

	public void run() {
		
		while (true) {
			try {
				if (!flag) {
					synchronized (this) {
						this.wait();
					}
				}
				Random myRandomName = new Random();
				int RN = myRandomName.nextInt(length);
				tf.setText(name.get(RN));
				Thread.sleep(1);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		Randomly rn = new Randomly();
		Thread t1 = new Thread(rn);
		t1.start();
	}
}
