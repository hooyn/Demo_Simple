//package solution.demo.domain.websocket.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@ServerEndpoint("/websocket")
//public class MessageController extends Socket {
//    private static final List<Session> session = new ArrayList<Session>();
//
//    @GetMapping("/chat")
//    public String index() {
//        return "chat";
//    }
//
//    @OnOpen
//    public void open(Session newUser) {
//        System.out.println(newUser.getId() + "님이 채팅방에 입장하였습니다.");
//        session.add(newUser);
//    }
//
//    @OnClose
//    public void close(Session user) {
//        System.out.println(user.getId() + "님이 채팅방에서 퇴장하셨습니다.");
//        session.remove(user);
//    }
//
//    @OnMessage
//    public void getMsg(Session receiveSession, String msg) {
//        for (int i = 0; i < session.size(); i++) {
//            if (!receiveSession.getId().equals(session.get(i).getId())) {
//                try {
//                    session.get(i).getBasicRemote().sendText(msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                try {
//                    session.get(i).getBasicRemote().sendText(msg + "[ME]");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
