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

async function findAll() {
    // Monta a URL para buscar todos os trabalhadores
    const url = `${baseUrl}/find-all`; // Substitua pela URL correta, caso necessário

    try {
        const response = await fetch(url);
        const data = await response.json();

        console.log("Dados recebidos:", data);

        // Extrai a lista de trabalhadores da propriedade "content"
        const trabalhadores = data.content;
        if (!trabalhadores || trabalhadores.length === 0) {
            console.log("Nenhum trabalhador encontrado.");
            return;
        }

        // Para cada trabalhador, cria uma linha na tabela
        trabalhadores.forEach(trabalhador => {
            // Cria um novo elemento de linha (<tr>)
            const novaLinha = document.createElement('tr');

            // Cria as células da tabela para exibir os dados do trabalhador
            const tdId = document.createElement('td');
            const tdNome = document.createElement('td');
            const tdCpf = document.createElement('td');
            const tdFuncao = document.createElement('td');
            const tdSalario = document.createElement('td');
            const tdAcoes = document.createElement('td');

            // Preenche as células com os dados do trabalhador
            tdId.innerText = trabalhador.id || "N/A";
            tdNome.innerText = trabalhador.nome || "N/A";
            tdCpf.innerText = trabalhador.cpf || "N/A";
            tdFuncao.innerText = trabalhador.funcao || "Não especificada";
            tdSalario.innerText = trabalhador.salario || "Não especificado";

            // Cria o botão de "Editar"
            const btnEditar = document.createElement('button');
            btnEditar.classList.add('btn', 'btn-secondary', 'btn-sm', 'me-2');
            btnEditar.textContent = 'Editar';
            // Ao clicar, redireciona para a tela de edição com o id do trabalhador na URL
            btnEditar.addEventListener('click', () => {
                window.location.href = `editar-trabalhador.html?id=${trabalhador.id}`;
            });

            // Cria o botão de "Excluir"
            const btnExcluir = document.createElement('button');
            btnExcluir.classList.add('btn', 'btn-danger', 'btn-sm');
            btnExcluir.textContent = 'Excluir';
            // Ao clicar, chama a função deleteUser passando o id do trabalhador
            btnExcluir.addEventListener('click', () => deleteUser(trabalhador.id));

            // Adiciona os botões na célula de ações
            tdAcoes.appendChild(btnEditar);
            tdAcoes.appendChild(btnExcluir);

            // Adiciona todas as células na linha criada
            novaLinha.appendChild(tdNome);
            novaLinha.appendChild(tdCpf);
            novaLinha.appendChild(tdSalario);
            novaLinha.appendChild(tdAcoes);

            // Adiciona a linha na tabela (elemento com id "tabelaTrabalhadores")
            document.getElementById('tabelaTrabalhadores').appendChild(novaLinha);
        });
    } catch (error) {
        console.error("Erro na requisição:", error); // Trata possíveis erros na requisição
    }
}

async function deleteUser(id) {
    const url = `${baseUrl}/delete?id=${id}`;

    if (confirm("Tem certeza de que deseja excluir este trabalhador?")) {
        try {
            const response = await fetch(url, {
                method: 'DELETE', // Método para exclusão
            });

            if (response.ok) {
                alert('Trabalhador excluído com sucesso!');
                findAll();
            } else {
                // Caso haja erro ao excluir
                const errorData = await response.json();
                console.error('Erro ao excluir trabalhador:', errorData);
                alert('Erro ao excluir trabalhador. Tente novamente.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Ocorreu um erro ao excluir o trabalhador.');
        }
    }
}

async function editTrabalhador(id) {
    // Coleta os dados do formulário de edição usando os IDs corretos
    const nome = document.getElementById('floatingInputNome').value;  // ID do nome
    const salario = document.getElementById('floatingInputSalario').value;  // ID do salário

    // Monta o objeto com os dados do trabalhador
    const userData = {
        nome: nome,
        salario: salario
    };

    // Monta a URL para atualizar o trabalhador
    const url = `${baseUrl}/collect-plus/v1/trabalhadores/update?id=${id}`; // URL de atualização com o ID do trabalhador

    try {
        const response = await fetch(url, {
            method: 'PATCH', // Método HTTP para atualização
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData) // Converte os dados para JSON
        });

        if (response.ok) {
            alert('Trabalhador atualizado com sucesso!');
            // Redireciona ou atualiza a lista de trabalhadores após a edição
            window.location.href = 'trabalhadores.html'; // Redireciona para a página de trabalhadores
        } else {
            // Caso haja erro, tenta extrair a mensagem de erro da resposta
            const errorData = await response.json();
            console.error('Erro ao atualizar trabalhador:', errorData);
            alert('Erro ao atualizar trabalhador. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao atualizar o trabalhador.');
    }
}

// Adiciona o evento ao botão de cadastro
document.addEventListener('DOMContentLoaded', function () {
    // Chama a função para buscar todos os trabalhadores ao carregar a página
    findAll();

});