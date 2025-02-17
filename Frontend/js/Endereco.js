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
                <td>${endereco.cidade || "N/A"}</td>
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
async function editarEndereco(id) {
    try {
        // Busca os dados do endereço
        const response = await fetch(`${baseUrl}/find-by-id?id=${id}`);
        if (!response.ok) {
            throw new Error(`Erro ao buscar endereço: ${response.statusText}`);
        }
        const endereco = await response.json();

        // Preenche os campos do formulário de edição
        document.getElementById("editEnderecoCep").value = endereco.cep;
        document.getElementById("editEnderecoCidade").value = endereco.cidade;
        document.getElementById("editEnderecoRua").value = endereco.rua;
        document.getElementById("editEnderecoNumero").value = endereco.numero;
        document.getElementById("editEnderecoComplemento").value = endereco.complemento;

        // Atualiza o título do modal para "Editar Endereço"
        document.querySelector(".modal-title").textContent = "Editar Endereço";

        // Exibe o modal usando Bootstrap
        const formModal = new bootstrap.Modal(document.getElementById("editEnderecoModal"));
        formModal.show();

        // Configura o evento de envio do formulário
        const form = document.getElementById("editEnderecoForm");
        form.onsubmit = async function (event) {
            event.preventDefault(); // Evita o envio padrão do formulário

            // Cria o objeto atualizado
            const updatedEndereco = {
                cep: document.getElementById("editEnderecoCep").value,
                cidade: document.getElementById("editEnderecoCidade").value,
                rua: document.getElementById("editEnderecoRua").value,
                numero: document.getElementById("editEnderecoNumero").value,
                complemento: document.getElementById("editEnderecoComplemento").value,
            };

            try {
                // Faz a requisição PATCH para atualizar os dados
                const updateResponse = await fetch(`${baseUrl}/update?id=${id}`, {
                    method: "PATCH",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(updatedEndereco),
                });

                if (updateResponse.ok) {
                    form.reset(); // Limpa o formulário
                    findAllEnderecos(); // Atualiza a tabela
                    alert("Endereço atualizado com sucesso!");
                    formModal.hide(); // Fecha o modal após a atualização
                } else {
                    alert("Erro ao atualizar endereço.");
                }
            } catch (error) {
                console.error("Erro ao atualizar endereço:", error);
            }
        };
    } catch (error) {
        console.error("Erro ao buscar endereço:", error);
    }
}

// Carrega os endereços ao abrir a página
document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById("tabelaEnderecosBody")) {
        findAllEnderecos();
    }
});
