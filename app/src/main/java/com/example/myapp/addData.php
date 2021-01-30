<?php
include("config.php");

$name = $_GET["name"];
$password = $_GET["password"];

$sql = "INSERT INTO user_login(user_name,password)values('$name',$password) ";

mysqli_query($conn, $sql);
mysqli_close($conn);
?>