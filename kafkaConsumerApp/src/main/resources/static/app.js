const stompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8082/gs-guide-websocket",
});
const kafkaStompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8082/kafka-websocket",
});
stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log("Connected: " + frame);
  stompClient.subscribe("/topic/greetings", (greeting) => {
    showGreeting(JSON.parse(greeting.body).content);
  });
};

stompClient.onWebSocketError = (error) => {
  console.error("Error with websocket", error);
};

stompClient.onStompError = (frame) => {
  console.error("Broker reported error: " + frame.headers["message"]);
  console.error("Additional details: " + frame.body);
};

kafkaStompClient.onConnect = (frame) => {
  setKafkaConnected(true);
  console.log("Connected: " + frame);
  kafkaStompClient.subscribe("/topic/kafkaIn", (greeting) => {
    showKafkaMsg(greeting.body);
  });
};

kafkaStompClient.onWebSocketError = (error) => {
  console.error("Error with  kafka websocket", error);
};

kafkaStompClient.onStompError = (frame) => {
  console.error("Broker reported error: " + frame.headers["message"]);
  console.error("Additional details: " + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#greetings").html("");
}
function setKafkaConnected(connected) {
  $("#kafkaConnect").prop("disabled", connected);
  $("#kafkaDisconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#kafkaStream").html("");
}
function connect() {
  stompClient.activate();
}
function kafkaConnect() {
  kafkaStompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}
function kafkaDisconnect() {
  kafkaStompClient.deactivate();
  setKafkaConnected(false);
  console.log("Kafka Disconnected");
}

function sendName() {
  stompClient.publish({
    destination: "/app/hello",
    body: JSON.stringify({ name: $("#name").val() }),
  });
}

function showGreeting(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function showKafkaMsg(message) {
  $("#kafkaStream").append("<tr><td>" + message + "</td></tr>");
}
$(function () {
  $("form").on("submit", (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#send").click(() => sendName());
  $("#kafkaConnect").click(() => kafkaConnect());
  $("#kafkaDisconnect").click(() => kafkaDisconnect());
});
