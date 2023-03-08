<?php
// Check if the request is a POST request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  // Check if a file was uploaded
  if (isset($_FILES['file'])) {
    // Get the uploaded file information
    $fileName = $_FILES['file']['name'];
    $fileTmpName = $_FILES['file']['tmp_name'];
    $fileSize = $_FILES['file']['size'];
    $fileError = $_FILES['file']['error'];
    $fileType = $_FILES['file']['type'];

    // Check if the file was uploaded successfully
    if ($fileError === UPLOAD_ERR_OK) {
      // Move the uploaded file to the htdocs directory
      move_uploaded_file($fileTmpName, $_SERVER['DOCUMENT_ROOT'] . '/PIDEV/BlogUploads/' . $fileName);
      echo "File uploaded successfully!";
    } else {
      echo "Error uploading file: " . $fileError;
    }
  } else {
    echo "No file uploaded!";
  }
}
