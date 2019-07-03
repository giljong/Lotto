import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;

interface Project{
    void MakeLotto(int arr[]);
    void LoadWebLotto(int arr[]);
    int CompareLotto(int arr1[],int arr2[]);
}

abstract class ALotto implements Project{
    public abstract void LoadWebLotto(int arr[]);
    public abstract int CompareLotto(int arr1[], int arr2[]);
    public void MakeLotto(int arr[]) {
        Random r = new Random();
        for(int i = 0;i<6;i++){
            arr[i] = r.nextInt(45) +1;
            for(int j = 0;j<i;j++){
                if(arr[i] == arr[j]) {
                    i--;
                    break;
                }
            }
        }
        System.out.print("유저의 로또번호는");
        for(int i = 0;i<6;i++){
            System.out.print(" " + arr[i]);
        }
        System.out.println();
    }
}

class WebLotto extends ALotto{
    public void LoadWebLotto(int arr[]){
        try{
            Document doc = Jsoup.connect("https://m.dhlottery.co.kr/common.do?method=main").get();
            String contents = doc.select("div.num").text();
            String[] text = contents.split(" ");
            System.out.print("이번주 당첨번호는");
            for(int i = 0;i<7;i++){
                System.out.print(" "+text[i]);
                arr[i] = Integer.parseInt(text[i]);
            }
            System.out.println();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public int CompareLotto(int arr1[],int arr2[]){
        int cnt = 0;
        for(int i = 0;i<6;i++){
            for(int j = 0;j<7;j++){
                if(arr1[i] == arr2[j]){
                    cnt++;
                }
            }
        }
        return cnt;
    }
}

public class Lotto {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("몇번이나 돌려볼까요?");
        int n = sc.nextInt();
        double ave = 0;
        for(int i = 0;i<n;i++) {
            WebLotto a = new WebLotto();
            int user[] = new int[10];
            int web[] = new int[10];
            a.MakeLotto(user);
            a.LoadWebLotto(web);
            System.out.println("맞은 개수 : " + a.CompareLotto(user, web) + "\n");
            ave += a.CompareLotto(user,web);
        }
        System.out.println("평균 맞은 개수 : " + ave /n);
    }
}