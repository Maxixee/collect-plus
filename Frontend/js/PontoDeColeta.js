const baseUrl = "http://localhost:8080/collect-plus/v1/pontos-de-coleta";

// Função para criar um ponto de coleta
async function createPontoDeColeta() {
    const endereco = document.getElementById("enderecoId").value;
    const tipoLixo = document.getElementById("tipoLixo").value;

    if (!endereco || !tipoLixo) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const data = {
        endereco: parseInt(endereco), 
        tipoLixo: tipoLixo
    };

    try {
        const response = await fetch(baseUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("Ponto de coleta cadastrado com sucesso!");
            window.location.href = "pontoc_cad.html"; // Redireciona para a listagem
        } else {
            const errorData = await response.json();
            alert(`Erro: ${errorData.message || "Erro ao cadastrar ponto de coleta."}`);
        }
    } catch (error) {
        console.error("Erro ao conectar com a API:", error);
        alert("Erro de conexão com o servidor.");
    }
}

// Função para listar todos os pontos de coleta
async function findAllPontosDeColeta() {
    try {
        const response = await fetch(`${baseUrl}/find-all`);

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
        }

        const data = await response.json(); // Objeto paginado
        const pontosDeColeta = data.content; // Lista real de pontos de coleta

        const tabelaBody = document.getElementById('tabelaPontosDeColetaBody');
        tabelaBody.innerHTML = ''; // Limpa a tabela antes de adicionar novos dados

        if (!pontosDeColeta || pontosDeColeta.length === 0) {
            tabelaBody.innerHTML = '<tr><td colspan="3" class="text-center">Nenhum ponto de coleta encontrado.</td></tr>';
            return;
        }

        pontosDeColeta.forEach(pontoDeColeta => {
            const novaLinha = document.createElement('tr');

            novaLinha.innerHTML = `
                <td>${pontoDeColeta.id || "N/A"}</td>
                <td>${pontoDeColeta.tipoLixo || "N/A"}</td>
                <td>
                    ${pontoDeColeta.endereco ? pontoDeColeta.endereco.cep : "N/A"}<br>
                    ${pontoDeColeta.endereco ? pontoDeColeta.endereco.rua : "N/A"}, ${pontoDeColeta.endereco ? pontoDeColeta.endereco.numero : "N/A"}<br>
                    ${pontoDeColeta.endereco ? pontoDeColeta.endereco.complemento : "N/A"}
                 </td>
                <td class="text-center">
                    <button class="btn btn-secondary btn-sm me-2" onclick="editarPontoDeColeta(${pontoDeColeta.id})">Editar</button>
                </td>
                <td class="text-center">
                    <button class="btn btn-danger btn-sm" onclick="deletePontoDeColeta(${pontoDeColeta.id})">Excluir</button>
                </td>
            `;

            tabelaBody.appendChild(novaLinha);
        });
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao buscar os pontos de coleta. Verifique o console para mais detalhes.");
    }
}

// Função para excluir um ponto de coleta
async function deletePontoDeColeta(id) {
    if (!confirm("Tem certeza de que deseja excluir este ponto de coleta?")) return;

    try {
        const response = await fetch(`${baseUrl}/delete?id=${id}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            alert('Ponto de coleta excluído com sucesso!');
            findAllPontosDeColeta(); // Atualiza a tabela após exclusão
        } else {
            const errorData = await response.json();
            console.error('Erro ao excluir ponto de coleta:', errorData);
            alert('Erro ao excluir ponto de coleta. Tente novamente.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao excluir o ponto de coleta.');
    }
}

// Função para editar um ponto de coleta (redireciona para a página de edição)
function editarPontoDeColeta(id) {
    window.location.href = `editar_ponto_coleta.html?id=${id}`;
}

// Carrega os pontos de coleta ao abrir a página
document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById("tabelaPontosDeColetaBody")) {
        findAllPontosDeColeta();
    }
});