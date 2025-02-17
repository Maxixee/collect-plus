const baseUrl = "http://localhost:8080/collect-plus/v1/trabalhadores";

async function carregarTrabalhadoresPorEquipe() {
    try {
        // Obtendo o ID da equipe da URL
        const urlParams = new URLSearchParams(window.location.search);
        const equipeId = urlParams.get('id');

        if (!equipeId) {
            console.error("ID da equipe não encontrado na URL.");
            return;
        }

        // Monta a URL para buscar os trabalhadores da equipe específica
        const url = `${baseUrl}/find-by-equipe-coleta?id=${equipeId}`;

        // Requisição para buscar trabalhadores da equipe
        const response = await fetch(url);
        const data = await response.json();

        console.log("Dados recebidos:", data);

        // Extrai a lista de trabalhadores da propriedade "content"
        const trabalhadores = data.content;

        if (!trabalhadores || trabalhadores.length === 0) {
            console.log("Nenhum trabalhador encontrado para esta equipe.");
            return;
        }

        // Obtém o corpo da tabela correta e limpa os dados anteriores
        const tabelaBody = document.querySelector("#tabelaTrabalhadoresEquipe tbody");
        if (!tabelaBody) {
            console.error("Elemento tbody não encontrado na tabela com ID 'tabelaTrabalhadoresEquipe'.");
            return;
        }

        tabelaBody.innerHTML = '';

        // Para cada trabalhador, cria uma linha na tabela
        trabalhadores.forEach(trabalhador => {
            const novaLinha = document.createElement('tr');

            const tdId = document.createElement('td');
            const tdNome = document.createElement('td');
            const tdCpf = document.createElement('td');
            const tdSalario = document.createElement('td');
            const tdAcoes = document.createElement('td');

            tdId.innerText = trabalhador.id || "N/A";
            tdNome.innerText = trabalhador.nome || "N/A";
            tdCpf.innerText = trabalhador.cpf || "N/A";
            tdSalario.innerText = trabalhador.salario ? parseFloat(trabalhador.salario).toFixed(2) : "Não especificado";

            // Cria o botão de "Editar"
            const btnEditar = document.createElement('button');
            btnEditar.classList.add('btn', 'btn-secondary', 'btn-sm', 'me-2');
            btnEditar.textContent = 'Editar';
            btnEditar.addEventListener('click', () => {
                window.location.href = `editar-trabalhador.html?id=${trabalhador.id}`;
            });

            // Cria o botão de "Excluir"
            const btnExcluir = document.createElement('button');
            btnExcluir.classList.add('btn', 'btn-danger', 'btn-sm');
            btnExcluir.textContent = 'Excluir';
            btnExcluir.addEventListener('click', () => deleteUser(trabalhador.id));

            // Adiciona os botões na célula de ações
            tdAcoes.appendChild(btnEditar);
            tdAcoes.appendChild(btnExcluir);

            // Adiciona todas as células na linha criada
            novaLinha.appendChild(tdId);
            novaLinha.appendChild(tdNome);
            novaLinha.appendChild(tdCpf);
            novaLinha.appendChild(tdSalario);
            novaLinha.appendChild(tdAcoes);

            // Adiciona a linha na tabela (elemento com id "tabelaTrabalhadoresEquipe")
            tabelaBody.appendChild(novaLinha);
        });
    } catch (error) {
        console.error("Erro ao carregar trabalhadores:", error);
    }
}

// Chama a função ao carregar a página
document.addEventListener('DOMContentLoaded', carregarTrabalhadoresPorEquipe);