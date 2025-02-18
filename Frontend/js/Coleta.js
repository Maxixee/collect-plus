const API_URL = "http://localhost:8080/collect-plus/v1/coletas";

// Função para cadastrar uma nova coleta
async function cadastrarColeta(event) {
    event.preventDefault(); // Evita recarregar a página

    const data = {
        pontoColeta: document.getElementById("pontoColeta").value,
        equipeColeta: document.getElementById("equipeColeta").value,
        frete: parseFloat(document.getElementById("frete").value)
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error("Erro ao cadastrar a coleta");
        }

        alert("Coleta cadastrada com sucesso!");
        document.getElementById("coletaForm").reset();
    } catch (error) {
        alert(error.message);
    }

    listarColetas();
}

async function listarColetas() {
  try {
    const response = await fetch('http://localhost:8080/collect-plus/v1/coletas/find-all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
    }

    const pageableDto = await response.json();
    const coletas = pageableDto.content;

    const tabelaBody = document.getElementById('listaColetas');
    tabelaBody.innerHTML = ''; // Limpa a tabela antes de adicionar novos dados

    if (coletas.length === 0) {
        tabelaBody.innerHTML = '<tr><td colspan="5" class="text-center">Nenhuma coleta encontrada.</td></tr>';
        return;
    }

    coletas.forEach(coleta => {
        const novaLinha = document.createElement('tr');
        novaLinha.innerHTML = `
            <td>${coleta.id || "N/A"}</td>
            <td>${coleta.equipeColetaId || "N/A"}</td>
            <td>${coleta.pontoColetaId || "N/A"}</td>
            <td>${coleta.frete || "N/A"}</td>
            <td class="text-center">
                <button class="btn btn-secondary btn-sm me-2" onclick="editarColeta(${coleta.id})">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="excluirColeta(${coleta.id})">Excluir</button>
            </td>
        `;
        tabelaBody.appendChild(novaLinha);
    });
} catch (error) {
    console.error("Erro na requisição:", error);
    alert("Erro ao buscar as coletas. Verifique o console para mais detalhes.");
}
}

// Função para editar uma coleta
async function editarColeta(id) {
  try {
      // Busca os dados da coleta
      const response = await fetch(`http://localhost:8080/collect-plus/v1/coletas/find-by-id?id=${id}`);
      if (!response.ok) {
          throw new Error(`Erro ao buscar coleta: ${response.statusText}`);
      }
      const coleta = await response.json();

      // Preenche os campos do formulário de edição
      document.getElementById("editColetaPontoId").value = coleta.pontoColeta.id;
      document.getElementById("editColetaEquipeId").value = coleta.equipeColeta.id;
      document.getElementById("editColetaFrete").value = coleta.frete;

      // Atualiza o título do modal para "Editar Endereço"
      document.querySelector(".modal-title").textContent = "Editar Coleta";

      // Exibe o modal usando Bootstrap
      const formModal = new bootstrap.Modal(document.getElementById("editColetaModal"));
      formModal.show();

      // Configura o evento de envio do formulário
      const form = document.getElementById("editColetaForm");
      form.onsubmit = async function (event) {
          event.preventDefault(); // Evita o envio padrão do formulário

          // Cria o objeto atualizado
          const updatedColeta = {
              equipeColetaId: document.getElementById("editColetaEquipeId").value,
              pontoColetaId: document.getElementById("editColetaPontoId").value,
              frete: document.getElementById("editColetaFrete").value,
          };

          try {
              // Faz a requisição PATCH para atualizar os dados
              const updateResponse = await fetch(`http://localhost:8080/collect-plus/v1/coletas/update?id=${id}`, {
                  method: "PATCH",
                  headers: { "Content-Type": "application/json" },
                  body: JSON.stringify(updatedColeta),
              });

              if (updateResponse.ok) {
                  form.reset(); // Limpa o formulário
                  alert("Coleta atualizada com sucesso!");
                  // Aqui você pode adicionar uma função para atualizar a lista de coletas, se necessário
                  listarColetas();
              } else {
                  alert("Erro ao atualizar coleta.");
              }
          } catch (error) {
              console.error("Erro ao atualizar coleta:", error);
          }
      };
  } catch (error) {
      console.error("Erro ao buscar coleta:", error);
  }
}

// Função para excluir uma coleta
async function excluirColeta(id) {
    try {
        const response = await fetch(`${API_URL}/delete?id=${id}`, { method: "DELETE" });
        if (!response.ok) {
            throw new Error("Erro ao excluir a coleta");
        }
        alert("Coleta excluída com sucesso!");
        listarColetas();
    } catch (error) {
        alert(error.message);
    }

    listarColetas();
}

// Adiciona o evento de submissão do formulário
document.getElementById("coletaForm").addEventListener("submit", cadastrarColeta);

// Chama a listagem ao carregar a página
document.addEventListener('DOMContentLoaded', listarColetas);
