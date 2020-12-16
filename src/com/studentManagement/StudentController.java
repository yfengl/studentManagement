package com.studentManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;

public class StudentController {

    int h = 0;//行数
    private final String[][] fileArray = new String[200][5];
    private boolean is_file = false;
    private String fName;
    @FXML
    private ListView<String> view;
    @FXML
    private TextField fileName;
    @FXML
    private TextField TjName;//新增姓名
    @FXML
    private TextField TjClass;//新增班级
    @FXML
    private TextField TjTel;//新增学号
    @FXML
    private TextField TjHTel;//新增年龄
    @FXML
    private TextField TjCode;//新增邮编
    @FXML
    private TextField message;//输入的查询信息
    @FXML
    private TextField cName, cClass, cTel, cHTel, cCode;
    @FXML
    private TextField xName, xClass, xTel, xHTel, xCode;
    @FXML
    private TextField TjMe, cMe, xMe, messageTs;

    public void initlize() throws IOException {
        fileName.setText("src/com/studentManagement/resources/Student.txt");
    }

    @FXML
    public void getFileName(ActionEvent e) throws IOException {
        initlize();
        fileWrite(fileArray, h);
        update(e);
        String s = "";
        if (fileName.getText().equals(s)) {
            is_file = false;
            TjMe.setText("请输入文件地址!");
            cMe.setText("请输入文件地址!");
            xMe.setText("请输入文件地址!");
        } else {
            fName = fileName.getText();
            is_file = true;
            TjMe.setText(null);
            cMe.setText(null);
            xMe.setText(null);
            fileRead();
        }
    }

    public void fileWrite(String[][] s, int n) throws IOException {
        if (is_file) {
            File file = new File(fName);
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter out = new OutputStreamWriter(fos);
            if (file.length() != 0) {
                out.write("\n");
            }
            if (n > 0) {
                for (int i = 0; i < n - 1; i++) {
                    out.write(s[i][0] + " " + s[i][1] + " " + s[i][2] + " " + s[i][3] + " " +
                            s[i][4] + "\n");
                }
                out.write(s[n - 1][0] + " " + s[n - 1][1] + " " + s[n - 1][2] + " " + s[n - 1][3]
                        + " " + s[n - 1][4]);
            }
            out.close();
        }
    }

    public void fileRead() throws IOException {
        if (is_file) {
            File file = new File(fName);
            if (!file.exists()) {
                file.createNewFile();
            }
            int k = 0;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                String[] strArr = s.split(" ");
                for (int i = 0; i < strArr.length; i++) {
                    fileArray[k][i] = strArr[i];
                }
                k++;
            }
            h = k;
            br.close();
        }
    }

    @FXML
    public void update(ActionEvent e) throws IOException {
        fileRead();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 0; i < h; i++) {
            list.addAll("学生" + (i + 1) + ":  学号:" + fileArray[i][0] + "   姓名:" + fileArray[i][1]
                    + "   年龄:" + fileArray[i][2] + "   性别:" + fileArray[i][3] + "   班级:" + fileArray[i][4]);

        }
        view.setItems(list);
    }

    @FXML
    public void tianJia(ActionEvent e) throws IOException {
        if (is_file) {
            TjMe.setText("请输入添加学生的信息");
            boolean tj = true;
            String tel = TjTel.getText();
            String name = TjName.getText();
            String classNum = TjClass.getText();
            String hTel = TjHTel.getText();
            String code = TjCode.getText();
            String s = "";
            if (name.equals(s) || hTel.equals(s) ||
                    classNum.equals(s) || code.equals(s) ||
                    tel.equals(s)) {
                TjMe.setText("信息不能为空！");
            } else {
                fileRead();
                for (int i = 0; i < h; i++) {
                    if (fileArray[i][0].equals(tel)) {
                        tj = false;
                        break;
                    }
                }
                if (tj) {
                    fileArray[h][0] = tel;
                    fileArray[h][1] = name;
                    fileArray[h][2] = hTel;
                    fileArray[h][3] = code;
                    fileArray[h][4] = classNum;

                    h++;
                    fileWrite(fileArray, h);
                    TjMe.setText("添加学生信息成功！！");
                    TjName.setText("");
                    TjClass.setText("");
                    TjTel.setText("");
                    TjHTel.setText("");
                    TjCode.setText("");
                    fileRead();
                } else TjMe.setText("添加失败，该学生已存在！");
            }
        } else {
            TjMe.setText("请输入文件地址!");
        }
    }


    @FXML
    public void chaXun(ActionEvent e) throws IOException {
        if (is_file) {
            cMe.setText("");
            boolean cx = false;
            String tel = cTel.getText();
            String classNum = null;
            String name = null;
            String hTel = null;
            String code = null;
            if (tel.equals("")) {
                cMe.setText("请输查询学号！不能为空！");
            } else {
                for (int i = 0; i < h; i++) {
                    if (fileArray[i][0].equals(tel)) {
                        name = fileArray[i][1];
                        hTel = fileArray[i][2];
                        code = fileArray[i][3];
                        classNum = fileArray[i][4];
                        cx = true;
                        break;
                    }
                }
                if (cx) {
                    cMe.setText("查询成功");
                    cName.setText(name);
                    cClass.setText(classNum);
                    cHTel.setText(hTel);
                    cCode.setText(code);
                } else {
                    cMe.setText("查询失败，该学生不存在！");
                    cName.setText("");
                    cClass.setText("");
                    cTel.setText("");
                    cHTel.setText("");
                    cCode.setText("");
                }
            }
        } else
            cMe.setText("请输入文件地址!");
    }

    @FXML
    public void lastCx(ActionEvent e) {
        boolean cx = false;
        String tel = cTel.getText();
        String classNum = null;
        String name = null;
        String hTel = null;
        String code = null;
        for (int i = 1; i < h; i++) {
            if (fileArray[i][0].equals(tel)) {
                tel = fileArray[i - 1][0];
                name = fileArray[i - 1][1];
                hTel = fileArray[i - 1][2];
                code = fileArray[i - 1][3];
                classNum = fileArray[i - 1][4];
                cx = true;
                break;
            }
        }
        if (cx) {
            cMe.setText("查询成功");
            cName.setText(name);
            cClass.setText(classNum);
            cTel.setText(tel);
            cHTel.setText(hTel);
            cCode.setText(code);
        } else {
            cMe.setText("查询失败，不存在上一条学生信息！");
            cName.setText("");
            cClass.setText("");
            cHTel.setText("");
            cCode.setText("");
        }
    }

    @FXML
    public void nextCx(ActionEvent e) {
        boolean cx = false;
        String tel = cTel.getText();
        String classNum = null;
        String name = null;
        String hTel = null;
        String code = null;
        for (int i = 0; i < h - 1; i++) {
            if (fileArray[i][0].equals(tel)) {
                tel = fileArray[i + 1][0];
                name = fileArray[i + 1][1];
                hTel = fileArray[i + 1][2];
                code = fileArray[i + 1][3];
                classNum = fileArray[i + 1][4];
                cx = true;
                break;
            }
        }
        if (cx) {
            cMe.setText("查询成功");
            cName.setText(name);
            cClass.setText(classNum);
            cTel.setText(tel);
            cHTel.setText(hTel);
            cCode.setText(code);
        } else {
            cMe.setText("查询失败，不存在下一条学生信息！");
            cName.setText("");
            cClass.setText("");
            cHTel.setText("");
            cCode.setText("");
        }
    }

    @FXML
    public void shanChu(ActionEvent e) throws IOException {
        if (is_file) {
            String tel = cTel.getText();
            boolean sc = false;
            if (tel.equals("")) cMe.setText("请输入删除学生学号！");
            else {
                for (int i = 0; i < h; i++) {
                    if (fileArray[i][0].equals(tel)) {
                        sc = true;
                        break;
                    }
                }
                if (sc) {
                    for (int i = 0; i < h; i++) {
                        if (fileArray[i][0].equals(tel)) {
                            for (int j = i; j < h - 1; j++) {
                                fileArray[j][0] = fileArray[j + 1][0];
                                fileArray[j][1] = fileArray[j + 1][1];
                                fileArray[j][2] = fileArray[j + 1][2];
                                fileArray[j][3] = fileArray[j + 1][3];
                                fileArray[j][4] = fileArray[j + 1][4];
                            }
                            break;
                        }
                    }
                    h = h - 1;
                    fileWrite(fileArray, h);
                    cMe.setText("删除学生信息成功！");
                    cName.setText("");
                    cClass.setText("");
                    cHTel.setText("");
                    cCode.setText("");
                } else {
                    cMe.setText("删除失败，不存在该学生");
                    cName.setText("");
                    cClass.setText("");
                    cTel.setText("");
                    cHTel.setText("");
                    cCode.setText("");
                }
            }
        } else cMe.setText("请输入文件地址!");
    }

    @FXML
    public void xiuGai(ActionEvent e) throws IOException {
        if (is_file) {
            boolean xg = false;
            String tel = xTel.getText();
            String classNum = xClass.getText();
            String name = xName.getText();
            String hTel = xHTel.getText();
            String code = xCode.getText();
            String s = "";
            if (name.equals(s) || hTel.equals(s) ||
                    classNum.equals(s) || code.equals(s) ||
                    tel.equals(s)) {
                xMe.setText("信息不能为空！");
            } else {
                for (int i = 0; i < h; i++) {
                    if (fileArray[i][0].equals(tel)) {
                        xg = true;
                        break;
                    }
                }
                if (xg) {
                    for (int i = 0; i < h; i++) {
                        if (fileArray[i][0].equals(tel)) {
                            fileArray[i][1] = name;
                            fileArray[i][2] = hTel;
                            fileArray[i][3] = code;
                            fileArray[i][4] = classNum;
                            break;
                        }
                    }
                    File file = new File(fName);
                    file.delete();
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    fileWrite(fileArray, h);
                    xMe.setText("修改学生信息成功！");
                } else {
                    xMe.setText("修改学生信息失败！学生不存在");
                    xName.setText("");
                }
            }
        } else xMe.setText("请输入文件地址!");
    }

    @FXML
    void xgCx(ActionEvent event) {
        String tel = xTel.getText();
        String classNum = null;
        String name = null;
        String hTel = null;
        String code = null;
        String s = "";
        if (tel.equals(s)) {
            xMe.setText("请输入学生学号！");
        } else {
            boolean cx = false;
            for (int i = 0; i < h; i++) {
                if (fileArray[i][0].equals(tel)) {
                    name = fileArray[i][1];
                    hTel = fileArray[i][2];
                    code = fileArray[i][3];
                    classNum = fileArray[i][4];
                    cx = true;
                    break;
                }
            }
            if (cx) {
                xMe.setText("查询成功");
                xName.setText(name);
                xClass.setText(classNum);
                xHTel.setText(hTel);
                xCode.setText(code);
            } else {
                xMe.setText("查询失败，该学生不存在！");
                xName.setText("");
                xClass.setText("");
                xTel.setText("");
                xHTel.setText("");
                xCode.setText("");
            }

        }
    }

    @FXML
    void getStudentsByMessage(ActionEvent event) {
        String mess = message.getText();
        if (mess.equals("")) {
            messageTs.setText("请输入信息");
        } else {
            boolean cx = false;
            String s="学号";
            ObservableList<String> list = FXCollections.observableArrayList();
            for(int j=0;j<5;j++){
                for (int i = 0; i < h; i++) {
                    if (fileArray[i][j].equals(mess)) {
                        list.addAll("学生" + (i + 1) + ":  学号:" + fileArray[i][0] + "   姓名:" + fileArray[i][1]
                                + "   年龄:" + fileArray[i][2] + "   性别:" + fileArray[i][3] + "   班级:" + fileArray[i][4]);
                        cx = true;
                        switch(j){
                            case 0: s="学号";break;
                            case 1:s="姓名";break;
                            case 2:s="性别";break;
                            case 3:s="年龄";break;
                            case 4:s="班级";break;
                            default:s="错误";break;
                        }
                    }
                }
            }
            if (cx) {
                view.setItems(list);
                messageTs.setText("按照”"+s+"“查询成功");
            } else {
                messageTs.setText("查询失败，该学生不存在！");
            }
        }

    }
}
