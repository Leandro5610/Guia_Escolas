const options = {
    method: "GET",
   
}

const cep = document.getElementById("cep")
cep.addEventListener("blur",() => {
    let Cep = document.getElementById("cep").value;
    console.log(Cep)
    let buscar = cep.value.replace("-", "")
    fetch(`https://viacep.com.br/ws/${buscar}/json/`, options).then((resp) => {
        resp.json().then(data => {
            console.log(data)
            document.getElementById("bairro").value = data.bairro;
             document.getElementById("uf").value = data.uf;
            document.getElementById("localidade").value = data.localidade;
            document.getElementById("logradouro").value = data.logradouro;
          

        })
    })
})

function enviar() {
    let bairro = document.getElementById("bairro").value;
    let localidade = document.getElementById("localidade").value;
    let logradouro = document.getElementById("logradouro").value;
   
    let json = {
        "bairro": bairro,
        "localidade": localidade,
        "logradouro": logradouro,
       
    }
    console.log(json)
}