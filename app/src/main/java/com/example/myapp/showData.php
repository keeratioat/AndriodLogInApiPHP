<?php
include("config.php");

$name = $_GET["name"];
$password = $_GET["password"];

$sql = "SELECT * FROM user_login WHERE user_name = '$name' AND password = '$password'";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
  // output data of each row
  while($row = mysqli_fetch_assoc($result)) {
    echo  $row['user_name'];
  }
} else {
  echo "No Account";
}

mysqli_close($conn);
?>