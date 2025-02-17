const baseUrl = "http://localhost:8080/collect-plus/v1/trabalhadores";
const params = new URLSearchParams(window.location.search);
const id = params.get('id');

async function createUser() {
    // Coleta os dados do formulário de criação
    const cpf = document.getElementById('floatingInputCpf').value;
    const nome = document.getElementById('floatingInputNome').value;
    const salario = document.getElementById('floatingInputSalario').value;

    // Monta o objeto com os dados do novo trabalhador
    const userData = {
        cpf: cpf,
        nome: nome,
        salario: salario
    };

    // Corrige a URL para o caminho correto
    const url = `${baseUrl}/create`; // O endpoint correto

    try {
        const response = await fetch(url, {
            method: 'POST', // Método HTTP para criação
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData) // Converte os dados para JSON
        });

        if (response.ok) {
            alert('Trabalhador cadastrado com sucesso!');
            // Redireciona para a tela de trabalhadores cadastrados após a criação
            window.location.href = 'trabalhadode_cad.html';
        } else {
            // Caso haja erro, tenta extrair a mensagem de erro da resposta
            const errorData = await response.json();
            console.error('Erro ao cadastrar trabalhador:', errorData);
            alert('Erro ao cadastrar trabalhador. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao cadastrar o trabalhador.');
    }
}

async function findAllEnderecos() {
    const url = `${baseUrl}/find-all`;

    try {
        const response = await fetch(url);
        const data = await response.json();

        console.log("Endereços recebidos:", data);

        // Extrai a lista de endereços da propriedade "content"
        const enderecos = data.content;
        if (!enderecos || enderecos.length === 0) {
            console.log("Nenhum endereço encontrado.");
            return;
        }

        // Seleciona a tabela de endereços
        const tabelaBody = document.getElementById('tabelaEnderecosBody');
        tabelaBody.innerHTML = ''; // Limpa a tabela antes de adicionar novos dados

        enderecos.forEach(endereco => {
            const novaLinha = document.createElement('tr');

            // Criando células
            const tdId = document.createElement('td');
            const tdCep = document.createElement('td');
            const tdRua = document.createElement('td');
            const tdNumero = document.createElement('td');
            const tdComplemento = document.createElement('td');
            const tdAcoes = document.createElement('td');

            // Preenchendo as células com os dados do endereço
            tdId.innerText = endereco.id || "N/A";
            tdCep.innerText = endereco.cep || "N/A";
            tdRua.innerText = endereco.rua || "N/A";
            tdNumero.innerText = endereco.numero || "N/A";
            tdComplemento.innerText = endereco.complemento || "N/A";

            // Criando botão "Editar"
            const btnEditar = document.createElement('button');
            btnEditar.classList.add('btn', 'btn-secondary', 'btn-sm', 'me-2');
            btnEditar.textContent = 'Editar';
            btnEditar.addEventListener('click', () => {
                window.location.href = `editar-endereco.html?id=${endereco.id}`;
            });

            // Criando botão "Excluir"
            const btnExcluir = document.createElement('button');
            btnExcluir.classList.add('btn', 'btn-danger', 'btn-sm');
            btnExcluir.textContent = 'Excluir';
            btnExcluir.addEventListener('click', () => deleteEndereco(endereco.id));

            // Adicionando botões à célula de ações
            tdAcoes.appendChild(btnEditar);
            tdAcoes.appendChild(btnExcluir);

            // Adicionando células à linha
            novaLinha.appendChild(tdCep);
            novaLinha.appendChild(tdRua);
            novaLinha.appendChild(tdNumero);
            novaLinha.appendChild(tdComplemento);
            novaLinha.appendChild(tdAcoes);

            // Adicionando linha à tabela
            tabelaBody.appendChild(novaLinha);
        });
    } catch (error) {
        console.error("Erro ao buscar os endereços:", error);
    }
}

async function deleteEndereco(id) {
    const url = `${baseUrl}/delete?id=${id}`;

    if (confirm("Tem certeza de que deseja excluir este endereço?")) {
        try {
            const response = await fetch(url, { method: 'DELETE' });

            if (response.ok) {
                alert('Endereço excluído com sucesso!');
                findAllEnderecos(); // Atualiza a lista após exclusão
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
}

async function editEndereco(id) {
    const cep = document.getElementById('floatingInputCep').value;
    const rua = document.getElementById('floatingInputRua').value;
    const numero = document.getElementById('floatingInputNumero').value;
    const complemento = document.getElementById('floatingInputComplemento').value;

    const enderecoData = {
        cep: cep,
        rua: rua,
        numero: numero,
        complemento: complemento
    };

    const url = `${baseUrl}/update?id=${id}`;

    try {
        const response = await fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(enderecoData)
        });

        if (response.ok) {
            alert('Endereço atualizado com sucesso!');
            window.location.href = 'enderecos.html';
        } else {
            const errorData = await response.json();
            console.error('Erro ao atualizar endereço:', errorData);
            alert('Erro ao atualizar endereço. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao atualizar o endereço.');
    }
}

// Chama a função ao carregar a página
document.addEventListener('DOMContentLoaded', function () {
    findAllEnderecos();
});
