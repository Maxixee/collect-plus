const baseUrl = "http://localhost:8080/collect-plus/v1/enderecos";

// Função para criar um endereço
async function createEndereco() {
    const cep = document.getElementById('floatingInputCep').value;
    const cidade = document.getElementById('floatingInputCidade').value;
    const rua = document.getElementById('floatingInputRua').value;
    const numero = document.getElementById('floatingInputNumero').value;
    const complemento = document.getElementById('floatingTextarea').value;

    const enderecoData = { cep, cidade, rua, numero, complemento };

    try {
        const response = await fetch(baseUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(enderecoData),
        });

        if (response.ok) {
            alert('Endereço cadastrado com sucesso!');
            window.location.href = 'endereco.html'; 
        } else {
            const errorData = await response.json();
            console.error('Erro ao cadastrar endereço:', errorData);
            alert('Erro ao cadastrar endereço. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao cadastrar o endereço.');
    }
}

async function findAllEnderecos() {
    try {
        const response = await fetch(`${baseUrl}/find-all`);

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
        }

        const pageableDto = await response.json(); // Recebe o objeto PageableDto

        // Acessa a lista de endereços dentro do objeto PageableDto
        const enderecos = pageableDto.content;

        const tabelaBody = document.getElementById('tabelaEnderecosBody');
        tabelaBody.innerHTML = ''; // Limpa a tabela antes de adicionar novos dados

        if (enderecos.length === 0) {
            tabelaBody.innerHTML = '<tr><td colspan="7" class="text-center">Nenhum endereço encontrado.</td></tr>';
            return;
        }

        enderecos.forEach(endereco => {
            const novaLinha = document.createElement('tr');

            novaLinha.innerHTML = `
                <td>${endereco.id || "N/A"}</td>
                <td>${endereco.cep || "N/A"}</td>
                <td>${endereco.rua || "N/A"}</td>
                <td>${endereco.numero || "N/A"}</td>
                <td>${endereco.complemento || "N/A"}</td>
                <td class="text-center">
                    <button class="btn btn-secondary btn-sm me-2" onclick="editarEndereco(${endereco.id})">Editar</button>
                </td>
                <td class="text-center">
                    <button class="btn btn-danger btn-sm" onclick="deleteEndereco(${endereco.id})">Excluir</button>
                </td>
            `;

            tabelaBody.appendChild(novaLinha);
        });
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao buscar os endereços. Verifique o console para mais detalhes.");
    }
}

// Função para excluir um endereço
async function deleteEndereco(id) {
    if (!confirm("Tem certeza de que deseja excluir este endereço?")) return;

    try {
        const response = await fetch(`${baseUrl}/delete?id=${id}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            alert('Endereço excluído com sucesso!');
            findAllEnderecos(); // Atualiza a tabela após exclusão
        } else {
            const errorData = await response.json();
            console.error('Erro ao excluir endereço:', errorData);
            alert('Erro ao excluir endereço. Tente novamente.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao excluir o endereço.');
    }
}

// Função para editar um endereço (redireciona para a página de edição)
function editarEndereco(id) {
    window.location.href = `.html?id=${id}`;
}

// Carrega os endereços ao abrir a página
document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById("tabelaEnderecosBody")) {
        findAllEnderecos();
    }
});
