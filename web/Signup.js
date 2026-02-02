let api = "http://40.0.4.43:8080/auth/signup";
async function submit() {
  let x = document.getElementById("n").value;
  let y = document.getElementById("e").value;
  let z = document.getElementById("p").value;

  let objData = {
    name: x,
    email: y,
    password: z,
  };
  console.log(" dto : " + JSON.stringify(objData, null, 2));

  try {
    let res = await fetch(api, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(objData),
    });
    let data = await res.text();
    console.log(" api response : " + data);
    if ((data = "Signup successful")) {
      alert(" signup sucesss .. ✅");
    } else {
      alert("signup failed....❌");
    }
  } catch (e) {
    console.log("error : " + e);
    alert("Server error");
  }
}
