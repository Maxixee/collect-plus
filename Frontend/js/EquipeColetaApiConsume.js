const baseUrl = "http://localhost:8080/collect-plus/v1/equipes-coleta";

async function createEquipeColeta() {
    const placaDoCarro = document.getElementById('floatingInput').value;

    const equipeData = { placaDoCarro };

    try {
        const response = await fetch(baseUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(equipeData)
        });

        if (response.create) {
            alert('Equipe de coleta criada com sucesso!');
        }

    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao criar a equipe de coleta.');
    }
}

async function findAllEquipes() {
    try {
        const response = await fetch(`${baseUrl}/find-all`);
        const data = await response.json();

        console.log("Dados recebidos:", data);

        // Extrai a lista de equipes da propriedade "content"
        const equipes = data.content;
        if (!equipes || equipes.length === 0) {
            console.log("Nenhuma equipe encontrada.");
            return;
        }

        // Para cada equipe, cria uma linha na tabela
        equipes.forEach(equipe => {
            // Cria um novo elemento de linha (<tr>)
            const novaLinha = document.createElement('tr');

            // Cria as células da tabela para exibir os dados da equipe
            const tdId = document.createElement('td');
            const tdPlacaDoCarro = document.createElement('td');
            const tdAcoes = document.createElement('td');

            // Preenche as células com os dados da equipe
            tdId.innerText = equipe.id || "N/A";
            tdPlacaDoCarro.innerText = equipe.placaDoCarro || "Não especificada";

            // Cria o botão de "Excluir"
            const btnExcluir = document.createElement('button');
            btnExcluir.classList.add('btn', 'btn-danger', 'btn-sm');
            btnExcluir.textContent = 'Excluir';
            // Ao clicar, chama a função deleteEquipe passando o id da equipe
            btnExcluir.addEventListener('click', () => deleteEquipe(equipe.id));

            // Cria o botão de "Ver Trabalhadores"
            const btnVerTrabalhadores = document.createElement('button');
            btnVerTrabalhadores.classList.add('btn', 'btn-danger', 'btn-sm');
            btnVerTrabalhadores.textContent = 'Ver Trabalhadores';
            btnVerTrabalhadores.addEventListener('click', () => {
                window.location.assign(`../trabalhadores/trabalhadores-by-equipe.html?id=${equipe.id}`);
            });

            // Adiciona os botões na célula de ações
            tdAcoes.appendChild(btnExcluir);
            tdAcoes.appendChild(btnVerTrabalhadores);

            // Adiciona todas as células na linha criada
            novaLinha.appendChild(tdId);
            novaLinha.appendChild(tdPlacaDoCarro);
            novaLinha.appendChild(tdAcoes);

            // Adiciona a linha na tabela (elemento com id "tableBody")
            document.getElementById('tableBody').appendChild(novaLinha);
        });
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}

async function deleteEquipe(id) {
    const url = `${baseUrl}/delete?id=${id}`;

    if (confirm("Tem certeza de que deseja excluir esta equipe?")) {
        try {
            const response = await fetch(url, { method: 'DELETE' });

            if (response.ok) {
                alert('Equipe excluída com sucesso!');
            } else {
                const errorData = await response.json();
                console.error('Erro ao excluir equipe:', errorData);
                alert('Erro ao excluir equipe. Tente novamente.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Ocorreu um erro ao excluir a equipe.');
        }
    }
}

async function adicionarTrabalhadorEquipe() {
    const trabalhadorId = document.getElementById('floatingInputTrabalhador').value;
    const equipeColetaId = document.getElementById('floatingInputEquipe').value;

    if (!trabalhadorId || !equipeColetaId) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    const trabalhadorEquipeData = { equipeColetaId, trabalhadorId };

    try {
        const response = await fetch(`${baseUrl}/adicionar-trabalhador`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(trabalhadorEquipeData)
        });

        if (response.ok) {
            alert('Trabalhador adicionado à equipe com sucesso!');
        } else {
            const errorData = await response.json();
            console.error('Erro ao adicionar trabalhador à equipe:', errorData);
            alert('Erro ao adicionar trabalhador à equipe. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao adicionar o trabalhador à equipe.');
    }
}

async function removerTrabalhadorEquipe() {
    const trabalhadorId = document.getElementById('floatingInputTrabalhador').value;
    const equipeColetaId = document.getElementById('floatingInputEquipe').value;

    if (!trabalhadorId || !equipeColetaId) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    const trabalhadorEquipeData = { equipeColetaId, trabalhadorId };

    try {
        const response = await fetch(`${baseUrl}/remover-trabalhador`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(trabalhadorEquipeData)
        });

        if (response.ok) {
            alert('Trabalhador removido da equipe com sucesso!');
        } else {
            const errorData = await response.json();
            console.error('Erro ao remover trabalhador da equipe:', errorData);
            alert('Erro ao remover trabalhador da equipe. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao remover o trabalhador da equipe.');
    }
}

document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.cadastrar-equipe').addEventListener('click', function (event) {
        event.preventDefault();
        createEquipeColeta();
        window.location.href = 'equipecad.html';
    });
});

