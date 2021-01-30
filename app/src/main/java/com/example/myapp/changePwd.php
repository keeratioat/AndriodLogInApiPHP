<?php
include("config.php");

$name = $_GET["name"];
$pass = $_GET["pass"];
$cpass = $_GET["cpass"];

$sql = "UPDATE `user_login` SET `password` = '$cpass' WHERE `user_name` = '$name' AND password = '$pass'";

mysqli_query($conn, $sql);
mysqli_close($conn);
?>