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

        if (response.ok) {
            alert('Equipe de coleta criada com sucesso!');
            window.location.href = 'equipes_cadastradas.html';
        } else {
            const errorData = await response.json();
            console.error('Erro ao criar equipe de coleta:', errorData);
            alert('Erro ao criar equipe de coleta. Verifique os dados informados.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Ocorreu um erro ao criar a equipe de coleta.');
    }
}

async function findAll() {
    try {
        const response = await fetch(`${baseUrl}/find-all`);
        const data = await response.json();

        if (!data.content || data.content.length === 0) {
            console.log("Nenhuma equipe encontrada.");
            return;
        }

        const tableBody = document.getElementById('tableBody');
        tableBody.innerHTML = ''; // Limpa a tabela antes de preencher

        data.content.forEach(equipe => {
            const novaLinha = document.createElement('tr');

            novaLinha.innerHTML = `
                <td>${equipe.id || "N/A"}</td>
                <td>${equipe.placaDoCarro || "Não especificada"}</td>
                <td>
                    <button class="btn btn-secondary btn-sm me-2" onclick="window.location.href='editar-adicionar-trabalhador.html?id=${equipe.id}'">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteEquipe(${equipe.id})">Excluir</button>
                </td>
            `;

            tableBody.appendChild(novaLinha);
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
                findAll(); // Atualiza a tabela após exclusão
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
            window.location.href = 'equipes_cadastradas.html';
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

document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.btn-primary').addEventListener('click', function (event) {
        event.preventDefault();
        createEquipeColeta();
    });

    findAll(); // Carrega as equipes ao carregar a página

    // Adiciona o evento de clique ao botão de adicionar trabalhador
    document.querySelector('.adicionar-trabalhador').addEventListener('click', function (event) {
        event.preventDefault(); // Previne o comportamento padrão do formulário (se houver)
        adicionarTrabalhadorEquipe(); // Chama a função para adicionar o trabalhador à equipe
    });
});

